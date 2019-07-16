package com.tsurkis.recipesearch.ui.screens.recipesearch

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tsurkis.recipesearch.app.ThreadManager
import com.tsurkis.recipesearch.data.repository.RecipeRepository
import com.tsurkis.recipesearch.data.repository.model.Recipe

class RecipeSearchViewModel : ViewModel() {

    var recipes: MutableLiveData<List<Recipe>> = MutableLiveData()
        private set

    var recipesSearchUIState: MutableLiveData<RecipesSearchUIState> = MutableLiveData()
        private set

    init {
        runOnBackThread {
            RecipeRepository
                .instance
                .getRecipes(
                    onSuccess = ::onRecipesRetrievedSuccessfully,
                    onFailure = ::onRecipesRetrievalFailure
                )
        }
    }

    fun searchRecipes(queryString: String?) {
        queryString ?: return
        this.recipesSearchUIState.value = RecipesSearchUIState(showLoader = true)
        runOnBackThread {
            RecipeRepository
                .instance
                .getRecipes(
                    queryString = queryString,
                    onSuccess = ::onRecipesRetrievedSuccessfully,
                    onFailure = ::onRecipesRetrievalFailure
                )
        }
    }

    private fun runOnBackThread(runnableBlock: () -> (Unit)) {
        ThreadManager
            .instance
            .ioThread
            .execute(runnableBlock)
    }

    private fun onRecipesRetrievedSuccessfully(recipes: List<Recipe>) {
        this.recipes.postValue(recipes)
        this.recipesSearchUIState.postValue(
            RecipesSearchUIState(
                showLoader = false,
                shouldDisplaySearchList = true,
                shouldShowSearch = recipes.isNotEmpty()
            )
        )
    }

    private fun onRecipesRetrievalFailure(throwable: Throwable) {
        val throwableMessage = throwable.message ?: ""
        this.recipesSearchUIState.postValue(
            RecipesSearchUIState(
                shouldDisplayError = true,
                errorText = throwableMessage
            )
        )
    }
}
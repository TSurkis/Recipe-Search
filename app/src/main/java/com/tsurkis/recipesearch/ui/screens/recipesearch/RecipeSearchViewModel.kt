package com.tsurkis.recipesearch.ui.screens.recipesearch

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tsurkis.recipesearch.app.ThreadManager
import com.tsurkis.recipesearch.data.repository.RecipeRepository
import com.tsurkis.recipesearch.data.repository.models.Recipe

class RecipeSearchViewModel : ViewModel() {

    var recipes: MutableLiveData<List<Recipe>> = MutableLiveData()
        private set

    var recipesSearchUIState: MutableLiveData<RecipesSearchUIState> = MutableLiveData()
        private set

    init {
        recipesSearchUIState.postValue(RecipesSearchUIState())
        recipes.postValue(listOf())
    }

    fun searchRecipes(queryString: String?) {
        queryString ?: return
        this.recipesSearchUIState.value = RecipesSearchUIState(showLoader = true)
        ThreadManager
            .instance
            .ioThread
            .execute {
                RecipeRepository
                    .instance
                    .searchRecipes(
                        queryString = queryString,
                        onSuccess = ::onRecipesRetrievedSuccessfully,
                        onFailure = ::onRecipesRetrievalFailure
                    )
            }
    }

    private fun onRecipesRetrievedSuccessfully(recipes: List<Recipe>) {
        this.recipes.postValue(recipes)
        this.recipesSearchUIState.postValue(
            RecipesSearchUIState(
                showLoader = false,
                shouldDisplaySearchList = true
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
package com.tsurkis.recipesearch.ui.screens.recipe.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tsurkis.recipesearch.data.repository.RecipeRepository
import com.tsurkis.recipesearch.data.repository.model.Recipe
import java.util.concurrent.Executor

class RecipeSearchViewModel(
    private val backThread: Executor,
    private val recipeRepository: RecipeRepository
) : ViewModel() {

    var recipes: MutableLiveData<List<Recipe>> = MutableLiveData()
        private set

    var recipesSearchUIState: MutableLiveData<RecipesSearchUIState> = MutableLiveData()
        private set

    private var previousSearchQuery: String? = null

    init {
        runOnBackThread {
            recipeRepository.getRecipes(
                onSuccess = ::onRecipesRetrievedSuccessfully,
                onFailure = ::onRecipesRetrievalFailure
            )
        }
    }

    fun searchRecipes(queryString: String?) {
        val trimmedQueryString = queryString?.trim() ?: ""
        if (trimmedQueryString.isNullOrBlank()) return
        if (previousSearchQuery.equals(trimmedQueryString)) {
            return
        } else {
            previousSearchQuery = trimmedQueryString
        }
        this.recipesSearchUIState.value = RecipesSearchUIState(showLoader = true)
        runOnBackThread {
            recipeRepository.getRecipes(
                queryString = trimmedQueryString,
                onSuccess = ::onRecipesRetrievedSuccessfully,
                onFailure = ::onRecipesRetrievalFailure
            )
        }
    }

    private fun runOnBackThread(runnableBlock: () -> (Unit)) {
        backThread.execute(runnableBlock)
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
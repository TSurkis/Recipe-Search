package com.tsurkis.recipesearch.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tsurkis.recipesearch.data.repository.RecipeRepository
import com.tsurkis.recipesearch.ui.screens.recipe.search.RecipeSearchViewModel
import java.util.concurrent.Executor

class ViewModelFactory(
    private val backThread: Executor,
    private val recipeRepository: RecipeRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(RecipeSearchViewModel::class.java) ->
                return RecipeSearchViewModel(
                    backThread = backThread,
                    recipeRepository = recipeRepository
                ) as T
            else -> throw RuntimeException("No ViewModel class found for ${modelClass.canonicalName}")
        }
    }
}
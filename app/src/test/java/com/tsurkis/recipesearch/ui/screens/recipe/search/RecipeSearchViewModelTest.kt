package com.tsurkis.recipesearch.ui.screens.recipe.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.tsurkis.recipesearch.data.repository.RecipeRepository
import com.tsurkis.recipesearch.data.repository.model.Recipe
import junit.framework.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.Executor

class RecipeSearchViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Test
    fun testSearchRecipesNullQueryString() {
        val viewModel = mockViewModel()
        val initialUiState = viewModel.recipesSearchUIState.value
        viewModel.searchRecipes(queryString = null)
        assertEquals(initialUiState,  viewModel.recipesSearchUIState.value)
    }

    @Test
    fun testSearchRecipesEmptyQueryString() {
        val viewModel = mockViewModel()
        val initialUiState = viewModel.recipesSearchUIState.value
        viewModel.searchRecipes(queryString = "")
        assertEquals(initialUiState,  viewModel.recipesSearchUIState.value)
    }

    @Test
    fun testSearchRecipesRepeatingQueryString() {
        val viewModel = mockViewModel()
        val queryString = "some ingredient"
        viewModel.searchRecipes(queryString = queryString)
        val initialUiState = viewModel.recipesSearchUIState.value
        viewModel.searchRecipes(queryString = queryString)
        assertEquals(initialUiState,  viewModel.recipesSearchUIState.value)
    }

    private fun mockViewModel(): RecipeSearchViewModel =
        RecipeSearchViewModel(
            backThread = ExecutorMock(),
            recipeRepository = RecipeRepositoryMock()
        )

    class ExecutorMock: Executor {
        override fun execute(runnable: Runnable) {
            runnable.run()
        }
    }

    class RecipeRepositoryMock : RecipeRepository {

        override fun getRecipes(
            queryString: String,
            onSuccess: (List<Recipe>) -> Unit,
            onFailure: (Throwable) -> Unit
        ) {
            onSuccess(listOf())
        }
    }
}
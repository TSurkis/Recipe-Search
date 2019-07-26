package com.tsurkis.recipesearch.data.repository

import com.tsurkis.recipesearch.data.local.api.RecipeDAOManager
import com.tsurkis.recipesearch.data.remote.api.RecipeSearchAPI
import com.tsurkis.recipesearch.data.repository.model.Recipe
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import org.junit.Test

class RecipeRepositoryTest {

    @Test
    fun testEmptyQueryString() {
        val remoteAPIMock = RecipeRemoteAPIMock()
        val recipeDaoManager = RecipeDaoManagerMock()
        val repository = RecipeRepositoryImplementation(
            recipeSearchAPI = remoteAPIMock,
            recipeDAOManager = recipeDaoManager
        )
        val queryString = ""
        repository.getRecipes(
            queryString = queryString,
            onSuccess = {},
            onFailure = {}
        )

        assertFalse(remoteAPIMock.didUse)
        assertFalse(recipeDaoManager.didUseSave)
        assertTrue(recipeDaoManager.didUseRetrieve)
    }

    @Test
    fun testNonEmptyQueryString() {
        val remoteAPIMock = RecipeRemoteAPIMock()
        val recipeDaoManager = RecipeDaoManagerMock()
        val repository = RecipeRepositoryImplementation(
            recipeSearchAPI = remoteAPIMock,
            recipeDAOManager = recipeDaoManager
        )
        val queryString = "Bublik"
        repository.getRecipes(
            queryString = queryString,
            onSuccess = {},
            onFailure = {}
        )

        assertTrue(remoteAPIMock.didUse)
        assertTrue(recipeDaoManager.didUseSave)
        assertFalse(recipeDaoManager.didUseRetrieve)
    }

    /*
        Mocks
     */

    private class RecipeRemoteAPIMock : RecipeSearchAPI {
        var didUse: Boolean = false
            private set

        override fun searchRecipes(
            queryString: String,
            onSuccess: (List<Recipe>) -> Unit,
            onFailure: (Throwable) -> Unit
        ) {
            didUse = true
            onSuccess(listOf())
        }
    }

    private class RecipeDaoManagerMock : RecipeDAOManager {
        var didUseSave: Boolean = false
            private set

        var didUseRetrieve: Boolean = false
            private set

        override fun save(recipes: List<Recipe>) {
            didUseSave = true
        }

        override fun retrieveLatest(): List<Recipe> {
            didUseRetrieve = true
            return listOf()
        }
    }
}

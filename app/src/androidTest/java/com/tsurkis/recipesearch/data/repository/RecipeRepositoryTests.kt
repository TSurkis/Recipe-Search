package com.tsurkis.recipesearch.data.repository

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.runner.AndroidJUnit4
import com.tsurkis.recipesearch.data.local.api.AppDatabase
import com.tsurkis.recipesearch.data.local.api.DatabaseCore
import com.tsurkis.recipesearch.data.local.api.RecipeDAOManager
import com.tsurkis.recipesearch.data.remote.api.RecipeSearchAPI
import com.tsurkis.recipesearch.data.repository.model.Recipe
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.stubbing.OngoingStubbing

@RunWith(AndroidJUnit4::class)
open class RecipeRepositoryTests {

    @Before
    fun setup() {
//        val context = ApplicationProvider.getApplicationContext<Context>()
//        val database =
//            Room.inMemoryDatabaseBuilder(
//                context,
//                AppDatabase::class.java
//            ).build()
//        Mockito.`when`(DatabaseCore.instance.recipeDao).then { database }
    }

    @Test
    fun testGetRecipesEmptyString() {
        var didGoThroughDatabase = false
        var didGoThroughRemoteAPI = false

        val queryString = ""

        val repositoryOnSuccessMock: (List<Recipe>) -> (Unit) = {}
        val repositoryOnFailureMock: (Throwable) -> (Unit) = { }
//        Mockito.`when`(RecipeDAOManager.retrieveLatest()).then {
//            didGoThroughDatabase = true
//            listOf<Recipe>()
//        }

        Mockito.`when`(
            RecipeSearchAPI.instance.searchRecipes(queryString, repositoryOnSuccessMock, repositoryOnFailureMock)
        ).then {
            didGoThroughRemoteAPI = true
            repositoryOnSuccessMock(listOf())
        }

        RecipeRepository.instance.getRecipes(
            queryString = queryString,
            onSuccess = repositoryOnSuccessMock,
            onFailure = repositoryOnFailureMock
        )

        assertTrue(didGoThroughDatabase)
        assertFalse(didGoThroughRemoteAPI)
    }

//    @Test
//    fun testGetRecipesFullString() {
//        var didGoThroughDatabase = false
//        var didGoThroughRemoteAPI = false
//
//        val queryString = "query string"
//
//        val repositoryOnSuccessMock: (List<Recipe>) -> (Unit) = {}
//        val repositoryOnFailureMock: (Throwable) -> (Unit) = { }
//        Mockito.`when`(DatabaseCore.instance).then {
//
//        }
//        Mockito.`when`(RecipeDAOManager::retrieveLatest).then {
//            didGoThroughDatabase = true
//            listOf<Recipe>()
//        }
//
//        Mockito.`when`(RecipeSearchAPI::searchRecipes).then {
//            didGoThroughRemoteAPI = true
//            listOf<Recipe>()
//        }
//
//        RecipeRepository.instance.getRecipes(
//            queryString = queryString,
//            onSuccess = repositoryOnSuccessMock,
//            onFailure = repositoryOnFailureMock
//        )
//
//        assertTrue(didGoThroughDatabase)
//        assertFalse(didGoThroughRemoteAPI)
//    }
}

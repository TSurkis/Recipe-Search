package com.tsurkis.recipesearch.data.local.api

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.runner.AndroidJUnit4
import com.tsurkis.recipesearch.data.local.model.RecipeLocalModel
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.junit.After
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppDatabaseTests {

    private lateinit var database: AppDatabase
    private lateinit var recipeDao: RecipeDAO

    private val recipeLocalModelMockList =
        listOf(
            RecipeLocalModel(
                name = "some_name",
                thumbnailUrl = "some_image",
                id = 376591
            )
        )

    @Before
    fun createDatabase() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database =
            Room
                .inMemoryDatabaseBuilder(
                    context,
                    AppDatabase::class.java
                )
                .build()
        recipeDao = database.recipeDao()
    }

    @After
    fun closeDatabase() {
        database.close()
    }

    @Test
    fun testInsert_regularList() {
        recipeDao.insert(recipeLocalModelMockList)

        assertEquals(recipeLocalModelMockList, recipeDao.getLatestSearch())
    }

    @Test
    fun testInsert_emptyList() {
        recipeDao.insert(listOf())
        assertTrue(recipeDao.getLatestSearch().isEmpty())
    }

    @Test
    fun testInsert_regularList_deletesOldOne() {
        recipeDao.insert(recipeLocalModelMockList)

        val anotherRecipeLocalModelMockList =
            listOf(
                RecipeLocalModel(
                    name = "another_name",
                    thumbnailUrl = "another_image",
                    id = 987654
                )
            )

        recipeDao.insert(anotherRecipeLocalModelMockList)

        assertEquals(anotherRecipeLocalModelMockList, recipeDao.getLatestSearch())
        assertNotEquals(recipeLocalModelMockList, recipeDao.getLatestSearch())
    }
}
package com.tsurkis.recipesearch.data.repository.model

import com.tsurkis.recipesearch.data.local.model.RecipeLocalModel
import com.tsurkis.recipesearch.data.remote.model.RecipeServerModel
import com.tsurkis.recipesearch.data.repository.model.Recipe
import com.tsurkis.recipesearch.data.repository.model.RecipeModelConverter
import junit.framework.Assert.assertEquals
import org.junit.Test

class RecipeModelConverterServerModelTest {

    private val converter = RecipeModelConverter()

    private val mockedName = "recipe_name"
    private val mockedThumbnail = "image"
    private val mockedId = 7618963

    private val mockedName2 = "recipe_name_2"
    private val mockedThumbnail2 = "image_2"
    private val mockedId2 = 2867439

    @Test
    fun test_fromServerModelToAppModel_regularList() {
        val serverModelList =
            listOf(
                RecipeServerModel(name = mockedName, thumbnailUrl = mockedThumbnail, id = mockedId),
                RecipeServerModel(name = mockedName2, thumbnailUrl = mockedThumbnail2, id = mockedId2)
            )
        val expectedAppModels =
            listOf(
                Recipe(name = mockedName, thumbnailUrl = mockedThumbnail, id = mockedId),
                Recipe(name = mockedName2, thumbnailUrl = mockedThumbnail2, id = mockedId2)
            )
        val actualAppModels = converter.fromServerModels(recipeServerModelList = serverModelList)
        assertEquals(expectedAppModels, actualAppModels)
    }

    @Test
    fun test_fromServerModelToAppModel_nullList() {
        assertEquals(
            listOf<Recipe>(),
            converter.fromServerModels(recipeServerModelList = null)
        )
    }

    @Test
    fun test_fromServerModelToAppModel_emptyList() {
        assertEquals(
            listOf<Recipe>(),
            converter.fromServerModels(recipeServerModelList = listOf())
        )
    }
}

class RecipeModelConverterDatabaseModelTest {

    private val converter = RecipeModelConverter()

    private val mockedName = "recipe_name"
    private val mockedThumbnail = "image"
    private val mockedId = 7618963

    private val mockedName2 = "recipe_name_2"
    private val mockedThumbnail2 = "image_2"
    private val mockedId2 = 2867439

    @Test
    fun test_fromServerModelToAppModel_regularList() {
        val serverModelList =
            listOf(
                RecipeLocalModel(name = mockedName, thumbnailUrl = mockedThumbnail, id = mockedId),
                RecipeLocalModel(name = mockedName2, thumbnailUrl = mockedThumbnail2, id = mockedId2)
            )
        val expectedAppModels =
            listOf(
                Recipe(name = mockedName, thumbnailUrl = mockedThumbnail, id = mockedId),
                Recipe(name = mockedName2, thumbnailUrl = mockedThumbnail2, id = mockedId2)
            )
        val actualAppModels = converter.fromLocalModels(recipeLocalModelList = serverModelList)
        assertEquals(expectedAppModels, actualAppModels)
    }

    @Test
    fun test_fromServerModelToAppModel_emptyList() {
        assertEquals(
            listOf<Recipe>(),
            converter.fromLocalModels(recipeLocalModelList = listOf())
        )
    }
}
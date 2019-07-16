package com.tsurkis.recipesearch.data.local.api

import androidx.room.Room
import com.tsurkis.recipesearch.app.RecipeSearchApp
import com.tsurkis.recipesearch.data.repository.model.Recipe
import com.tsurkis.recipesearch.data.repository.model.RecipeModelConverter

class DatabaseManager private constructor() {
    companion object {
        val instance = DatabaseManager()
    }

    private val database =
        Room
            .databaseBuilder(
                RecipeSearchApp.instance.applicationContext,
                AppDatabase::class.java,
                DatabaseNames.databaseName
            )
            .build()
    private val converter: RecipeModelConverter = RecipeModelConverter()

    fun save(recipes: List<Recipe>) {
        val recipeLocalModels = converter.toLocalModels(recipes)
        database
            .recipeDao()
            .insert(recipeLocalModels)
    }

    fun retrieveLatest(): List<Recipe> {
        val recipeLocalModels =
            database
                .recipeDao()
                .getLatestSearch()
        return converter.fromLocalModels(recipeLocalModels)
    }
}
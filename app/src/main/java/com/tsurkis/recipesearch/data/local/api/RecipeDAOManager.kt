package com.tsurkis.recipesearch.data.local.api

import com.tsurkis.recipesearch.data.repository.model.Recipe
import com.tsurkis.recipesearch.data.repository.model.RecipeModelConverter

class RecipeDAOManager {
    companion object {
        val instance = RecipeDAOManager()
    }

    private val converter: RecipeModelConverter = RecipeModelConverter()

    fun save(recipes: List<Recipe>) {
        val recipeLocalModels = converter.toLocalModels(recipes)
        DatabaseCore
            .instance
            .recipeDao
            .insert(recipeLocalModels)
    }

    fun retrieveLatest(): List<Recipe> {
        val recipeLocalModels =
            DatabaseCore
                .instance
                .recipeDao
                .getLatestSearch()
        return converter.fromLocalModels(recipeLocalModels)
    }
}
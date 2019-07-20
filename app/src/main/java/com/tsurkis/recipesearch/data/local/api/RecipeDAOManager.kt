package com.tsurkis.recipesearch.data.local.api

import com.tsurkis.recipesearch.data.repository.model.Recipe
import com.tsurkis.recipesearch.data.repository.model.RecipeModelConverter

interface RecipeDAOManager {
    fun save(recipes: List<Recipe>)
    fun retrieveLatest(): List<Recipe>
}

class RecipeDAOManagerImplementation(
    private val dao: RecipeDAO,
    private val converter: RecipeModelConverter
) : RecipeDAOManager {

    override fun save(recipes: List<Recipe>) {
        val recipeLocalModels = converter.toLocalModels(recipes)
        dao.insert(recipeLocalModels)
    }

    override fun retrieveLatest(): List<Recipe> {
        val recipeLocalModels = dao.getLatestSearch()
        return converter.fromLocalModels(recipeLocalModels)
    }
}
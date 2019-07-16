package com.tsurkis.recipesearch.data.repository.model

import com.tsurkis.recipesearch.data.local.model.RecipeLocalModel
import com.tsurkis.recipesearch.data.remote.model.RecipeServerModel

class RecipeModelConverter {
    private fun from(recipeServerModel: RecipeServerModel): Recipe =
        Recipe(
            name = recipeServerModel.name,
            thumbnailUrl = recipeServerModel.thumbnailUrl,
            id = recipeServerModel.id
        )

    private fun from(recipeLocalModel: RecipeLocalModel): Recipe =
        Recipe(
            name = recipeLocalModel.name,
            thumbnailUrl = recipeLocalModel.thumbnailUrl,
            id = recipeLocalModel.id
        )

    private fun toLocalModel(recipe: Recipe): RecipeLocalModel =
        RecipeLocalModel(
            name = recipe.name,
            thumbnailUrl = recipe.thumbnailUrl,
            id = recipe.id
        )

    fun fromServerModels(recipeServerModelList: List<RecipeServerModel>?): List<Recipe> =
        recipeServerModelList
            ?.map { recipeServerModel ->
                from(recipeServerModel)
            } ?: listOf()

    fun fromLocalModels(recipeLocalModelList: List<RecipeLocalModel>): List<Recipe> =
        recipeLocalModelList
            .map { recipeLocalModel ->
                from(recipeLocalModel)
            }

    fun toLocalModels(recipes: List<Recipe>): List<RecipeLocalModel> =
        recipes
            .map { recipe ->
                toLocalModel(recipe)
            }
}
package com.tsurkis.recipesearch.data.repository.models

import com.tsurkis.recipesearch.data.remote.models.RecipeServerModel

class RecipeModelConverter {
    private fun from(recipeServerModel: RecipeServerModel) =
        Recipe(
            name = recipeServerModel.name,
            thumbnailUrl = recipeServerModel.thumbnailUrl,
            id = recipeServerModel.id
        )

    fun from(recipeServerModelList: List<RecipeServerModel>?) =
        recipeServerModelList
            ?.map { recipeServerModel ->
                from(recipeServerModel)
            } ?: listOf()
}
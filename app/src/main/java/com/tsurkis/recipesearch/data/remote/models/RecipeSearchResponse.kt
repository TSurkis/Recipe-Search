package com.tsurkis.recipesearch.data.remote.models

import com.google.gson.annotations.SerializedName

data class RecipeSearchResponse(
    @SerializedName("meals")
    val recipes: List<RecipeServerModel>
)

data class RecipeServerModel(
    @SerializedName("strMeal")
    val name: String,
    @SerializedName("strMealThumb")
    val thumbnailUrl: String,
    @SerializedName("idMeal")
    val id: Int
)

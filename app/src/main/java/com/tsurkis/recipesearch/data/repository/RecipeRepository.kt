package com.tsurkis.recipesearch.data.repository

import com.tsurkis.recipesearch.data.remote.api.RecipeSearchAPI
import com.tsurkis.recipesearch.data.repository.models.Recipe

class RecipeRepository private constructor() {
    companion object {
        val instance: RecipeRepository = RecipeRepository()
    }

    fun searchRecipes(queryString: String, onSuccess: (List<Recipe>) -> (Unit), onFailure: (Throwable) -> (Unit)) {
        RecipeSearchAPI
            .instance
            .searchRecipes(
                queryString = queryString,
                onSuccess = onSuccess,
                onFailure = onFailure
            )
    }
}
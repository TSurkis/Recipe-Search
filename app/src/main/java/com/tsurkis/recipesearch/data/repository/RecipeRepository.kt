package com.tsurkis.recipesearch.data.repository

import com.tsurkis.recipesearch.data.local.api.RecipeDAOManager
import com.tsurkis.recipesearch.data.remote.api.RecipeSearchAPI
import com.tsurkis.recipesearch.data.repository.model.Recipe

class RecipeRepository private constructor() {
    companion object {
        val instance: RecipeRepository = RecipeRepository()
    }

    fun getRecipes(queryString: String = "", onSuccess: (List<Recipe>) -> (Unit), onFailure: (Throwable) -> (Unit)) {
        if (queryString.isNotEmpty()) {
            RecipeSearchAPI
                .instance
                .searchRecipes(
                    queryString = queryString,
                    onSuccess = { recipes ->
                        RecipeDAOManager
                            .instance
                            .save(recipes)
                        onSuccess(recipes)
                    },
                    onFailure = onFailure
                )
        } else {
            val locallySavedRecipes = RecipeDAOManager.instance.retrieveLatest()
            onSuccess(locallySavedRecipes)
        }
    }
}
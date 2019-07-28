package com.tsurkis.recipesearch.data.repository

import com.tsurkis.recipesearch.data.local.api.RecipeDAOManager
import com.tsurkis.recipesearch.data.remote.api.RecipeSearchAPI
import com.tsurkis.recipesearch.data.repository.model.Recipe

interface RecipeRepository {
    fun getRecipes(queryString: String = "", onSuccess: (List<Recipe>) -> (Unit), onFailure: (Throwable) -> (Unit))
}

class RecipeRepositoryImplementation(
    private val recipeSearchAPI: RecipeSearchAPI,
    private val recipeDAOManager: RecipeDAOManager
) : RecipeRepository {

    override fun getRecipes(
        queryString: String,
        onSuccess: (List<Recipe>) -> (Unit),
        onFailure: (Throwable) -> (Unit)
    ) {
        if (queryString.isNotEmpty()) {
            recipeSearchAPI
                .searchRecipes(
                    queryString = queryString,
                    onSuccess = { recipes ->
                        recipeDAOManager.save(recipes)
                        onSuccess(recipes)
                    },
                    onFailure = onFailure
                )
        } else {
            val locallySavedRecipes = recipeDAOManager.retrieveLatest()
            onSuccess(locallySavedRecipes)
        }
    }
}
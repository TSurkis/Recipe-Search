package com.tsurkis.recipesearch.injection

import com.tsurkis.recipesearch.data.local.api.RecipeDAOManager
import com.tsurkis.recipesearch.data.remote.api.RecipeSearchAPI
import com.tsurkis.recipesearch.data.repository.RecipeRepository
import com.tsurkis.recipesearch.data.repository.RecipeRepositoryImplementation

class RepositoryModule {

    fun provideRecipeRepository(
        recipeRemoteAPI: RecipeSearchAPI,
        recipeDAOManager: RecipeDAOManager
    ): RecipeRepository =
        RecipeRepositoryImplementation(
            recipeSearchAPI = recipeRemoteAPI,
            recipeDAOManager = recipeDAOManager
        )
}
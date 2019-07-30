package com.tsurkis.recipesearch.injection

import com.tsurkis.recipesearch.data.local.api.RecipeDAOManager
import com.tsurkis.recipesearch.data.remote.api.RecipeSearchAPI
import com.tsurkis.recipesearch.data.repository.RecipeRepository
import com.tsurkis.recipesearch.data.repository.RecipeRepositoryImplementation
import org.koin.dsl.module

val repositoryModule = module {

    single<RecipeRepository> {
        RecipeRepositoryImplementation(
            recipeSearchAPI = get<RecipeSearchAPI>(),
            recipeDAOManager = get<RecipeDAOManager>()
        )
    }
}
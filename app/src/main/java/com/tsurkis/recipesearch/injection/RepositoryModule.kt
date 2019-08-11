package com.tsurkis.recipesearch.injection

import com.tsurkis.recipesearch.data.repository.RecipeRepository
import com.tsurkis.recipesearch.data.repository.RecipeRepositoryImplementation
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoryModule = module {

    single {
        RecipeRepositoryImplementation(
            recipeSearchAPI = get(),
            recipeDAOManager = get()
        )
    } bind RecipeRepository::class
}
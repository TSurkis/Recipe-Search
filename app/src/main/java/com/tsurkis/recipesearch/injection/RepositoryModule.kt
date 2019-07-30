package com.tsurkis.recipesearch.injection

import com.tsurkis.recipesearch.data.repository.RecipeRepository
import com.tsurkis.recipesearch.data.repository.RecipeRepositoryImplementation
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {

    @Binds
    @ApplicationScope
    abstract fun provideRecipeRepository(recipeSearchRepositoryImplementation: RecipeRepositoryImplementation): RecipeRepository
}
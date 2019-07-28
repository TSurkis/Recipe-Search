package com.tsurkis.recipesearch.injection

import com.tsurkis.recipesearch.app.ViewModelFactory
import com.tsurkis.recipesearch.data.repository.RecipeRepository
import java.util.concurrent.Executor

class ViewModelFactoryModule {

    fun provideViewModelFactory(
        backThread: Executor,
        recipeRepository: RecipeRepository
    ) =
        ViewModelFactory(
            backThread = backThread,
            recipeRepository = recipeRepository
        )
}
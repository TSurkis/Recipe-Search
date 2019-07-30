package com.tsurkis.recipesearch.injection

import com.tsurkis.recipesearch.injection.DependencyNames.ioThread
import com.tsurkis.recipesearch.ui.screens.recipe.search.RecipeSearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val viewModelsModule = module {

    viewModel {
        RecipeSearchViewModel(
            backThread = get(named(name = ioThread)),
            recipeRepository = get()
        )
    }
}
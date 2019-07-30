package com.tsurkis.recipesearch.injection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tsurkis.recipesearch.app.ViewModelFactory
import com.tsurkis.recipesearch.ui.screens.recipe.search.RecipeSearchViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelsModule {

    @Binds
    abstract fun provideViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(RecipeSearchViewModel::class)
    abstract fun provideRecipeSearchViewModel(recipeSearchViewModel: RecipeSearchViewModel): ViewModel
}
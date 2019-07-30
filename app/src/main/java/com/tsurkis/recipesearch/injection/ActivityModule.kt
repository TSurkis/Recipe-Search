package com.tsurkis.recipesearch.injection

import com.tsurkis.recipesearch.ui.screens.recipe.search.RecipeSearchActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun provideRecipeSearchActivity(): RecipeSearchActivity
}
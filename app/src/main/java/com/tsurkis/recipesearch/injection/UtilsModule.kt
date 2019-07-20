package com.tsurkis.recipesearch.injection

import com.tsurkis.recipesearch.data.repository.model.RecipeModelConverter

class UtilsModule {

    fun provideRecipeModuleConverter(): RecipeModelConverter =
        RecipeModelConverter()
}
package com.tsurkis.recipesearch.injection

import com.squareup.picasso.Picasso
import com.tsurkis.recipesearch.data.repository.model.RecipeModelConverter
import dagger.Module
import dagger.Provides

@Module
class UtilsModule {

    @Provides
    @ApplicationScope
    fun provideRecipeModuleConverter(): RecipeModelConverter = RecipeModelConverter()

    @Provides
    @ApplicationScope
    fun provideImageLoaderInstance(): Picasso = Picasso.get()
}
package com.tsurkis.recipesearch.injection

import com.squareup.picasso.Picasso
import com.tsurkis.recipesearch.custom.wrappers.ImageLoader
import com.tsurkis.recipesearch.data.repository.model.RecipeModelConverter

class UtilsModule {

    fun provideRecipeModuleConverter(): RecipeModelConverter = RecipeModelConverter()

    fun provideImageLoaderInstance(): Picasso = Picasso.get()

    fun provideImageLoaderWrapper(imageLoaderInstance: Picasso) = ImageLoader(imageLoaderInstance = imageLoaderInstance)
}
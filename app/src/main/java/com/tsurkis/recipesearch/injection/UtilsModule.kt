package com.tsurkis.recipesearch.injection

import com.squareup.picasso.Picasso
import com.tsurkis.recipesearch.custom.wrappers.ImageLoader
import com.tsurkis.recipesearch.data.repository.model.RecipeModelConverter
import org.koin.dsl.module

val utilsModule  = module {

    single<RecipeModelConverter> {
        RecipeModelConverter()
    }

    single<Picasso> {
        Picasso.get()
    }

    single<ImageLoader> {
        ImageLoader(imageLoaderInstance = get<Picasso>())
    }
}
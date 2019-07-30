package com.tsurkis.recipesearch.injection

import androidx.room.Room
import com.tsurkis.recipesearch.data.local.api.*
import com.tsurkis.recipesearch.data.repository.model.RecipeModelConverter
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val localAPIModule = module {

    single<AppDatabase> {
        Room
            .databaseBuilder(
                androidContext(),
                AppDatabase::class.java,
                DatabaseNames.databaseName
            )
            .build()
    }

    single<RecipeDAO> {
        val database  = get<AppDatabase>()
        database.recipeDao()
    }

    single<RecipeDAOManager> {
        RecipeDAOManagerImplementation(
            dao = get<RecipeDAO>(),
            converter = get<RecipeModelConverter>()
        )
    }
}
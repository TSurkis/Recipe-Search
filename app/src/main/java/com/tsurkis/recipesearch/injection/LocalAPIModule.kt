package com.tsurkis.recipesearch.injection

import androidx.room.Room
import com.tsurkis.recipesearch.data.local.api.*
import com.tsurkis.recipesearch.data.repository.model.RecipeModelConverter
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.bind
import org.koin.dsl.module

val localAPIModule = module {

    single {
        Room
            .databaseBuilder(
                androidContext(),
                AppDatabase::class.java,
                DatabaseNames.databaseName
            )
            .build()
    } bind AppDatabase::class

    single {
        val database  = get<AppDatabase>()
        database.recipeDao()
    }

    single {
        RecipeDAOManagerImplementation(
            dao = get(),
            converter = get()
        )
    } bind RecipeDAOManager::class
}
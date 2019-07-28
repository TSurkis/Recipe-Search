package com.tsurkis.recipesearch.injection

import android.content.Context
import androidx.room.Room
import com.tsurkis.recipesearch.data.local.api.*
import com.tsurkis.recipesearch.data.repository.model.RecipeModelConverter

class LocalAPIModule {

    fun provideAppDatabase(applicationContext: Context): AppDatabase =
        Room
            .databaseBuilder(
                applicationContext,
                AppDatabase::class.java,
                DatabaseNames.databaseName
            )
            .build()

    fun provideRecipeDao(appDatabase: AppDatabase): RecipeDAO =
        appDatabase.recipeDao()

    fun provideRecipeDAOManager(
        recipeDao: RecipeDAO,
        converter: RecipeModelConverter
    ): RecipeDAOManager =
        RecipeDAOManagerImplementation(
            dao = recipeDao,
            converter = converter
        )
}
package com.tsurkis.recipesearch.data.local.api

import androidx.room.Room
import com.tsurkis.recipesearch.app.RecipeSearchApp

class DatabaseCore private constructor() {
    companion object {
        val instance = DatabaseCore()
    }

    private val database =
        Room
            .databaseBuilder(
                RecipeSearchApp.instance.applicationContext,
                AppDatabase::class.java,
                DatabaseNames.databaseName
            )
            .build()

    val recipeDao = database.recipeDao()
}
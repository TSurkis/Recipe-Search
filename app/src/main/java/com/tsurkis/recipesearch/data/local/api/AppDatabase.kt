package com.tsurkis.recipesearch.data.local.api

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tsurkis.recipesearch.data.local.model.RecipeLocalModel

@Database(entities = [RecipeLocalModel::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun recipeDao(): RecipeDAO
}
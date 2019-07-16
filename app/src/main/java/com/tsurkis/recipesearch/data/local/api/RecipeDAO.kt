package com.tsurkis.recipesearch.data.local.api

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Transaction
import com.tsurkis.recipesearch.data.local.api.DatabaseNames.recipeTable
import com.tsurkis.recipesearch.data.local.model.RecipeLocalModel

@Dao
abstract class RecipeDAO {

    @Insert(onConflict = REPLACE)
    protected abstract fun insertRecipes(recipes: List<RecipeLocalModel>)

    @Query("DELETE FROM $recipeTable")
    protected abstract fun deleteAllData()

    @Query("SELECT * FROM $recipeTable")
    abstract fun getLatestSearch(): List<RecipeLocalModel>

    @Transaction
    open fun insert(recipes: List<RecipeLocalModel>) {
        deleteAllData()
        insertRecipes(recipes)
    }
}
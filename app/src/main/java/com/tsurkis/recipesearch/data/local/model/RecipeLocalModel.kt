package com.tsurkis.recipesearch.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tsurkis.recipesearch.data.local.api.DatabaseNames.recipeTable

@Entity(tableName = recipeTable)
data class RecipeLocalModel(
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "thumbnail_url")
    val thumbnailUrl: String
)
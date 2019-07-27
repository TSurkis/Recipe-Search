package com.tsurkis.recipesearch.ui.screens.recipe.search

data class RecipesSearchUIState(
    val shouldDisplayError: Boolean = false,
    val shouldShowSearch: Boolean = true,
    val errorText: String = "",
    val showLoader: Boolean = false,
    val shouldDisplaySearchList: Boolean = false
)
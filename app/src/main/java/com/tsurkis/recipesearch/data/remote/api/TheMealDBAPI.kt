package com.tsurkis.recipesearch.data.remote.api

import com.tsurkis.recipesearch.data.remote.models.RecipeSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TheMealDBAPI {

    @GET("filter.php")
    fun searchRecipes(
        @Query("i") queryString: String
    ): Call<RecipeSearchResponse>
}
package com.tsurkis.recipesearch.data.remote.api

import com.tsurkis.recipesearch.data.remote.core.RetrofitClient
import com.tsurkis.recipesearch.data.remote.models.RecipeSearchResponse
import com.tsurkis.recipesearch.data.repository.models.Recipe
import com.tsurkis.recipesearch.data.repository.models.RecipeModelConverter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RecipeSearchAPI private constructor() {
    companion object {
        val instance: RecipeSearchAPI = RecipeSearchAPI()
    }

    private val api: TheMealDBAPI = RetrofitClient.instance.core.create(TheMealDBAPI::class.java)
    private val recipeModelConverter = RecipeModelConverter()

    fun searchRecipes(queryString: String, onSuccess: (List<Recipe>) -> (Unit), onFailure: (Throwable) -> (Unit)) {
        api
            .searchRecipes(queryString = queryString)
            .enqueue(object : Callback<RecipeSearchResponse?> {
                override fun onFailure(call: Call<RecipeSearchResponse?>, throwable: Throwable) {
                    onFailure(throwable)
                }

                override fun onResponse(call: Call<RecipeSearchResponse?>, response: Response<RecipeSearchResponse?>) {
                    response
                        .body()
                        ?.let { recipeSearchResponse ->
                            onSuccess(recipeModelConverter.from(recipeSearchResponse.recipes))
                        }
                        ?: onFailure(Throwable())
                }
            })
    }
}
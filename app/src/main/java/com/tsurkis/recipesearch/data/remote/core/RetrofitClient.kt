package com.tsurkis.recipesearch.data.remote.core

import com.tsurkis.recipesearch.BuildConfig
import com.tsurkis.recipesearch.app.ThreadManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient private constructor() {
    companion object {
        val instance = RetrofitClient()
    }

    val core: Retrofit

    init {
        val interceptor = HttpLoggingInterceptor()
            .apply {
                level = HttpLoggingInterceptor.Level.BASIC
            }
        val okHttpClient =
            OkHttpClient
                .Builder()
                .addInterceptor(interceptor)
                .build()
        core =
            Retrofit
                .Builder()
                .callbackExecutor(ThreadManager.instance.networkThread)
                .baseUrl(BuildConfig.API_END_POINT)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
    }
}
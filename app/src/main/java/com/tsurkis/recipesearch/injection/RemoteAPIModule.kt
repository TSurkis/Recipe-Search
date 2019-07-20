package com.tsurkis.recipesearch.injection

import com.tsurkis.recipesearch.BuildConfig
import com.tsurkis.recipesearch.data.remote.api.RecipeSearchAPIImplementation
import com.tsurkis.recipesearch.data.remote.api.TheMealDBAPI
import com.tsurkis.recipesearch.data.repository.model.RecipeModelConverter
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executor

class RemoteAPIModule {

    fun provideInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor()
            .apply {
                level = HttpLoggingInterceptor.Level.BASIC
            }

    fun provideOKHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(loggingInterceptor)
            .build()

    fun provideGsonConverterFactory(): Converter.Factory = GsonConverterFactory.create()

    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        remoteAPIBackThread: Executor,
        converterFactory: Converter.Factory
    ): Retrofit =
        Retrofit
            .Builder()
            .callbackExecutor(remoteAPIBackThread)
            .baseUrl(BuildConfig.OPINIONATED_API_END_POINT)
            .addConverterFactory(converterFactory)
            .client(okHttpClient)
            .build()

    fun provideTheMealDBAPIAPI(retrofitClient: Retrofit): TheMealDBAPI =
        retrofitClient.create(TheMealDBAPI::class.java)

    fun provideRecipeSearchAPI(
        api: TheMealDBAPI,
        converter: RecipeModelConverter
    ) =
        RecipeSearchAPIImplementation(
            api = api,
            converter = converter
        )
}
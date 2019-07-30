package com.tsurkis.recipesearch.injection

import com.tsurkis.recipesearch.BuildConfig
import com.tsurkis.recipesearch.data.remote.api.RecipeSearchAPI
import com.tsurkis.recipesearch.data.remote.api.RecipeSearchAPIImplementation
import com.tsurkis.recipesearch.data.remote.api.TheMealDBAPI
import com.tsurkis.recipesearch.data.repository.model.RecipeModelConverter
import com.tsurkis.recipesearch.injection.DependencyNames.networkThread
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executor

val remoteAPIModule = module {

    single<HttpLoggingInterceptor> {
        HttpLoggingInterceptor()
            .apply {
                level = HttpLoggingInterceptor.Level.BASIC
            }
    }

    single<OkHttpClient> {
        OkHttpClient
            .Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }

    single<Converter.Factory> {
        GsonConverterFactory.create()
    }

    single<Retrofit> {
        Retrofit
            .Builder()
            .callbackExecutor(get<Executor>(named(name = networkThread)))
            .baseUrl(BuildConfig.API_END_POINT)
            .addConverterFactory(get<Converter.Factory>())
            .client(get<OkHttpClient>())
            .build()
    }

    single<TheMealDBAPI> {
        val retrofit = get<Retrofit>()
        retrofit.create(TheMealDBAPI::class.java)
    }

    single<RecipeSearchAPI> {
        RecipeSearchAPIImplementation(
            api = get<TheMealDBAPI>(),
            converter = get<RecipeModelConverter>()
        )
    }
}
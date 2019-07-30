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
import org.koin.dsl.bind
import org.koin.dsl.binds
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executor

val remoteAPIModule = module {

    single {
        HttpLoggingInterceptor()
            .apply {
                level = HttpLoggingInterceptor.Level.BASIC
            }
    } bind HttpLoggingInterceptor::class

    single {
        OkHttpClient
            .Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    } bind OkHttpClient::class

    single {
        GsonConverterFactory.create()
    } bind Converter.Factory::class

    single {
        Retrofit
            .Builder()
            .callbackExecutor(get<Executor>(named(name = networkThread)))
            .baseUrl(BuildConfig.API_END_POINT)
            .addConverterFactory(get<Converter.Factory>())
            .client(get<OkHttpClient>())
            .build()
    } bind Retrofit::class

    single {
        val retrofit = get<Retrofit>()
        retrofit.create(TheMealDBAPI::class.java)
    } bind TheMealDBAPI::class

    single {
        RecipeSearchAPIImplementation(
            api = get(),
            converter = get()
        )
    } bind RecipeSearchAPI::class
}
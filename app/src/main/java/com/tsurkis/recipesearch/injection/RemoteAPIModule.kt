package com.tsurkis.recipesearch.injection

import com.tsurkis.recipesearch.BuildConfig
import com.tsurkis.recipesearch.data.remote.api.RecipeSearchAPI
import com.tsurkis.recipesearch.data.remote.api.RecipeSearchAPIImplementation
import com.tsurkis.recipesearch.data.remote.api.TheMealDBAPI
import com.tsurkis.recipesearch.injection.DependencyNames.networkThread
import dagger.Binds
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executor
import javax.inject.Named

@Module
class RemoteAPIModule {

    @Provides
    fun provideInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor()
            .apply {
                level = HttpLoggingInterceptor.Level.BASIC
            }

    @Provides
    fun provideOKHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(loggingInterceptor)
            .build()

    @Provides
    fun provideGsonConverterFactory(): Converter.Factory = GsonConverterFactory.create()

    @Provides
    @ApplicationScope
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        @Named(networkThread) remoteAPIBackThread: Executor,
        converterFactory: Converter.Factory
    ): Retrofit =
        Retrofit
            .Builder()
            .callbackExecutor(remoteAPIBackThread)
            .baseUrl(BuildConfig.API_END_POINT)
            .addConverterFactory(converterFactory)
            .client(okHttpClient)
            .build()

    @Provides
    @ApplicationScope
    fun provideTheMealDBAPI(retrofitClient: Retrofit): TheMealDBAPI =
        retrofitClient.create(TheMealDBAPI::class.java)
}

@Module
abstract class RemoteAPIBindingModule {
    @Binds
    @ApplicationScope
    abstract fun provideRecipeSearchAPI(recipeSearchAPIImplementation: RecipeSearchAPIImplementation): RecipeSearchAPI
}
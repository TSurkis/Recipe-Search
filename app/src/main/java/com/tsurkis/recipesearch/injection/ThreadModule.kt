package com.tsurkis.recipesearch.injection

import com.tsurkis.recipesearch.injection.DependencyNames.ioThread
import com.tsurkis.recipesearch.injection.DependencyNames.networkThread
import dagger.Module
import dagger.Provides
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Named

@Module
class ThreadModule {

    @Provides
    @ApplicationScope
    @Named(ioThread)
    fun provideIOThread(): Executor = Executors.newSingleThreadExecutor()

    @Provides
    @ApplicationScope
    @Named(networkThread)
    fun provideNetworkThread(): Executor = Executors.newFixedThreadPool(3)
}
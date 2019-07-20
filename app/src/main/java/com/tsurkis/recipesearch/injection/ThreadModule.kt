package com.tsurkis.recipesearch.injection

import com.tsurkis.recipesearch.app.ThreadManager
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class ThreadModule {

    fun provideIOThread(): Executor = Executors.newSingleThreadExecutor()

    fun provideNetworkThread(): Executor = Executors.newFixedThreadPool(3)

    fun provideThreadManager(
        networkThread: Executor,
        ioThread: Executor
    ): ThreadManager =
        ThreadManager(
            networkThread = networkThread,
            ioThread = ioThread
        )
}
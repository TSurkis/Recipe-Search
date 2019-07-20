package com.tsurkis.recipesearch.app

import java.util.concurrent.Executor
import java.util.concurrent.Executors

class ThreadManager private constructor(
    val networkThread: Executor,
    val ioThread: Executor
) {

    companion object {
        val instance =
            ThreadManager(
                networkThread = Executors.newFixedThreadPool(3),
                ioThread = Executors.newSingleThreadExecutor()
            )
    }
}
package com.tsurkis.recipesearch.app

import java.util.concurrent.Executor
import java.util.concurrent.Executors

class ThreadManager(
    val networkThread: Executor,
    val ioThread: Executor
)
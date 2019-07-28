package com.tsurkis.recipesearch.app

import java.util.concurrent.Executor

class ThreadManager(
    val networkThread: Executor,
    val ioThread: Executor
)
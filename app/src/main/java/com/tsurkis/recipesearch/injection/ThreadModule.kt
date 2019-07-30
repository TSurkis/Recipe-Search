package com.tsurkis.recipesearch.injection

import com.tsurkis.recipesearch.injection.DependencyNames.ioThread
import com.tsurkis.recipesearch.injection.DependencyNames.networkThread
import org.koin.core.qualifier.named
import org.koin.dsl.module
import java.util.concurrent.Executors

val threadModule = module {

    single(named(name = ioThread)) {
        Executors.newSingleThreadExecutor()
    }

    single(named(name = networkThread)) {
        Executors.newFixedThreadPool(3)
    }
}
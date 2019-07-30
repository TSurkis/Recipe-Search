package com.tsurkis.recipesearch.injection

import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class Injector {

    companion object {
        private val modules = listOf(
            localAPIModule,
            remoteAPIModule,
            repositoryModule,
            threadModule,
            utilsModule,
            viewModelsModule
        )

        fun instantiate(applicationContext: Context) {
            startKoin {
                androidLogger()
                androidContext(androidContext = applicationContext)
                modules(modules)
            }
        }
    }
}
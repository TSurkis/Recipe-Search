package com.tsurkis.recipesearch.injection

import androidx.test.core.app.ApplicationProvider
import androidx.test.runner.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.check.checkModules

@RunWith(AndroidJUnit4::class)
class DependencyGraphTest : KoinTest {

    @Test
    fun testKoinDependencyGraphImplementation() {
        /*
            Since this is an instrumentation test, Koin was already initialized in our Application class.
            Thus, to start it for testing, we need to stop the existing one.
         */
        stopKoin()
        startKoin {
            androidContext(androidContext = ApplicationProvider.getApplicationContext())
            modules(
                listOf(
                    localAPIModule,
                    remoteAPIModule,
                    repositoryModule,
                    threadModule,
                    utilsModule,
                    viewModelsModule
                )
            )
        }.checkModules()
    }
}
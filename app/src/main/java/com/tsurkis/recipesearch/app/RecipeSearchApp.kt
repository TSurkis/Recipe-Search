package com.tsurkis.recipesearch.app

import android.app.Application
import com.tsurkis.recipesearch.injection.Injector

class RecipeSearchApp : Application() {

    companion object {
        lateinit var instance: RecipeSearchApp
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        Injector.instantiate(applicationContext = applicationContext)
    }
}
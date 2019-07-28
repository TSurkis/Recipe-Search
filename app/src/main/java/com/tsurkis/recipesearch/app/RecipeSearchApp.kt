package com.tsurkis.recipesearch.app

import android.app.Application
import com.tsurkis.recipesearch.injection.Injector

class RecipeSearchApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Injector.instantiate(applicationContext = applicationContext)
    }
}
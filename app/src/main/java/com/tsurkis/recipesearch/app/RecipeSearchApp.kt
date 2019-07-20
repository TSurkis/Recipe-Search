package com.tsurkis.recipesearch.app

import android.app.Application

class RecipeSearchApp : Application() {

    companion object {
        lateinit var instance: RecipeSearchApp
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}
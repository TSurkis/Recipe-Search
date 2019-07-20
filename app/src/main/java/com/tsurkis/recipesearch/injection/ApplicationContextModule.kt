package com.tsurkis.recipesearch.injection

import android.content.Context

class ApplicationContextModule(
    private val applicationContext: Context
) {

    fun provideApplicationContext() = applicationContext
}
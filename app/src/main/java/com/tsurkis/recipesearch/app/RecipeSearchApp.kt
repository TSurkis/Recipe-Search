package com.tsurkis.recipesearch.app

import com.tsurkis.recipesearch.injection.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class RecipeSearchApp : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val applicationComponent =
            DaggerApplicationComponent
                .builder()
                .application(applicationContext = this)
                .build()

        applicationComponent.inject(this)

        return applicationComponent
    }
}
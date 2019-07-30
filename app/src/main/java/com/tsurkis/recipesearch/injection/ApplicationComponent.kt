package com.tsurkis.recipesearch.injection

import android.content.Context
import com.tsurkis.recipesearch.app.RecipeSearchApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector

@ApplicationScope
@Component(
    modules = [
        AndroidInjectionModule::class,
        ActivityModule::class,
        LocalAPIModule::class,
        LocalAPIBindingModule::class,
        RemoteAPIModule::class,
        RemoteAPIBindingModule::class,
        RepositoryModule::class,
        ThreadModule::class,
        UtilsModule::class,
        ViewModelsModule::class
    ]
)
interface ApplicationComponent : AndroidInjector<RecipeSearchApp> {

    @Component.Builder
    interface Builder {
        fun build(): ApplicationComponent
        @BindsInstance
        fun application(@ApplicationContext applicationContext: Context): Builder
    }
}
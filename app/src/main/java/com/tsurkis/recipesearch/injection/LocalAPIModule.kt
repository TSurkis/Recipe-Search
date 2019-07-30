package com.tsurkis.recipesearch.injection

import android.content.Context
import androidx.room.Room
import com.tsurkis.recipesearch.data.local.api.*
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
class LocalAPIModule {

    @Provides
    @ApplicationScope
    fun provideAppDatabase(@ApplicationContext applicationContext: Context): AppDatabase =
        Room
            .databaseBuilder(
                applicationContext,
                AppDatabase::class.java,
                DatabaseNames.databaseName
            )
            .build()

    @Provides
    @ApplicationScope
    fun provideRecipeDao(appDatabase: AppDatabase): RecipeDAO =
        appDatabase.recipeDao()
}

@Module
abstract class LocalAPIBindingModule {

    @Binds
    @ApplicationScope
    abstract fun provideRecipeDAOManager(recipeDAOManagerImplementation: RecipeDAOManagerImplementation): RecipeDAOManager
}
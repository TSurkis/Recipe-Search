package com.tsurkis.recipesearch.injection

import androidx.lifecycle.ViewModel
import dagger.MapKey
import javax.inject.Qualifier
import javax.inject.Scope
import kotlin.reflect.KClass

@Qualifier
@Retention(AnnotationRetention.SOURCE)
annotation class ApplicationContext

@Scope
@Retention(AnnotationRetention.SOURCE)
annotation class ApplicationScope

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
@MapKey
annotation class ViewModelKey(val keyValue: KClass<out ViewModel>)
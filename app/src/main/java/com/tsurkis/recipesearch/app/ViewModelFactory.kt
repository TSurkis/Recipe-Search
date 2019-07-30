package com.tsurkis.recipesearch.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tsurkis.recipesearch.injection.ApplicationScope
import javax.inject.Inject
import javax.inject.Provider

@ApplicationScope
class ViewModelFactory @Inject constructor(
    private val viewModels: MutableMap<Class<out ViewModel>, Provider<ViewModel>>
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>) =
        viewModels[modelClass]?.get() as T
}
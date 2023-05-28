package com.example.fitness.di.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

class ViewModelFactory @Inject constructor(
    private val creators: @JvmSuppressWildcards Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
            val found = creators.entries.find { modelClass.isAssignableFrom(it.key) }
            val creator = found?.value ?: throw IllegalArgumentException("Unknown model class $modelClass")

            @Suppress("UNCHECKED_CAST")
            return creator.get() as T
    }

}
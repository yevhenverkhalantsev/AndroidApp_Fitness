package com.example.fitness.screens.context.injection.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fitness.registration.RegistrationFragment
import com.example.fitness.registration.viewmodel.RegistrationViewModel
import com.example.fitness.screens.context.injection.annotations.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.Reusable
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Reusable
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(RegistrationViewModel::class)
    abstract fun bindRegistrationViewModel(viewModel: RegistrationViewModel): ViewModel

    @ContributesAndroidInjector
    abstract fun contributeRegistrationFragment(): RegistrationFragment
}
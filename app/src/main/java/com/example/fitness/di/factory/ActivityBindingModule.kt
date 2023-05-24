package com.example.fitness.di.factory

import com.example.fitness.screens.context.activity.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ActivityBindingModule {

    @ContributesAndroidInjector(modules = [ViewModelModule::class])
    fun mainActivity(): MainActivity

}
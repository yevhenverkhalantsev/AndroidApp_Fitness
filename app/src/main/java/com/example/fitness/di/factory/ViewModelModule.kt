package com.example.fitness.di.factory

import com.example.fitness.screens.login.LoginFragment
import com.example.fitness.screens.login.viewmodel.LoginViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fitness.screens.description.view.DescriptionExercisesFragment
import com.example.fitness.screens.registration.RegistrationFragment
import com.example.fitness.screens.registration.viewmodel.RegistrationViewModel
import com.example.fitness.di.annotations.ViewModelKey
import com.example.fitness.screens.planfragment.view.PlanFragment
import com.example.fitness.screens.planfragment.viewmodel.PlanViewModel
import com.example.fitness.screens.exercises.exercisePerforming.PerformingViewModel
import com.example.fitness.screens.exercises.exercisePerforming.view.PerformingExercisesFragment
import com.example.fitness.screens.exercises.exercises.view.ExercisesFragment
import com.example.fitness.screens.exercises.workouts.WorkoutsFragment
import com.example.fitness.screens.exercises.workouts.viewmodel.ExercisesViewModel
import dagger.Binds
import dagger.Module
import dagger.Reusable
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {
    @Reusable
    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(RegistrationViewModel::class)
    fun bindRegistrationViewModel(viewModel: RegistrationViewModel): ViewModel

    @ContributesAndroidInjector
    fun contributeRegistrationFragment(): RegistrationFragment

    @Binds
    @IntoMap
    @ViewModelKey(PerformingViewModel::class)
    fun bindPerformingViewModel(viewModel: PerformingViewModel): ViewModel

    @ContributesAndroidInjector
    fun contributePerformingFragment(): PerformingExercisesFragment

    @Binds
    @IntoMap
    @ViewModelKey(ExercisesViewModel::class)
    fun bindExercisesViewModel(viewModel: ExercisesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    fun bindLoginViewModel(viewModel: LoginViewModel): ViewModel

    @ContributesAndroidInjector
    fun contributeLoginFragment(): LoginFragment


    @ContributesAndroidInjector
    fun bindWorkoutFragment(): WorkoutsFragment


    @ContributesAndroidInjector
    fun bindExercisesFragment(): ExercisesFragment

    @ContributesAndroidInjector
    fun bindPlanFragment(): PlanFragment


    @ContributesAndroidInjector
    fun bindDescriptionFragment(): DescriptionExercisesFragment

    @Binds
    @IntoMap
    @ViewModelKey(PlanViewModel::class)
    fun bindPlanViewModel(viewModel: PlanViewModel): ViewModel
}
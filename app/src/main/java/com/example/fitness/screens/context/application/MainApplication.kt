package com.example.fitness.screens.context.application

import android.content.Context
import com.example.fitness.screens.description.view.DescriptionExercisesFragment
import com.example.fitness.screens.exercises.exercises.view.ExercisesFragment
import com.example.fitness.screens.registration.RegistrationFragment
import com.example.fitness.retrofit.ApiModule
import com.example.fitness.di.annotations.ApplicationContext
import com.example.fitness.di.factory.ActivityBindingModule
import com.example.fitness.di.factory.ViewModelModule
import com.example.fitness.screens.exercises.exercisePerforming.view.PerformingExercisesFragment
import com.example.fitness.screens.exercises.workouts.WorkoutsFragment
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import javax.inject.Singleton


@Singleton
@Component(modules = [ApiModule::class, AndroidInjectionModule::class, ViewModelModule::class, ActivityBindingModule::class])
interface AppComponent: AndroidInjector<MainApplication> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance @ApplicationContext context: Context): AppComponent
    }
    fun inject(descriptionExercisesFragment: DescriptionExercisesFragment)
    fun inject(exercisesFragment: ExercisesFragment)
    fun inject(workoutsFragment: WorkoutsFragment)
    fun inject(registrationFragment: RegistrationFragment)

    fun inject(performingExercisesFragment: PerformingExercisesFragment)
}
class MainApplication: DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.factory().create(this)
    }

}

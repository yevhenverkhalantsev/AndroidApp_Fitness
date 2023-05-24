package com.example.fitness.screens.context.application

import android.content.Context
import com.example.fitness.description.view.DescriptionExercisesFragment
import com.example.fitness.screens.exercises.someother.view.ExercisesFragment
import com.example.fitness.main_window.MainWindowFragment
import com.example.fitness.registration.RegistrationFragment
import com.example.fitness.retrofit.ApiModule
import com.example.fitness.di.annotations.ApplicationContext
import com.example.fitness.di.factory.ActivityBindingModule
import com.example.fitness.di.factory.ViewModelModule
import com.example.fitness.di.modules.UtilsModule
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

//    @Component.Builder
//    interface Builder {
//        @BindsInstance
//        fun application(application: Application): Builder
//        fun build(): AppComponent
//    }
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance @ApplicationContext context: Context): AppComponent
    }
    fun inject(descriptionExersicesFragment: DescriptionExercisesFragment)
    fun inject(exercisesFragment: ExercisesFragment)
    fun inject(workoutsFragment: WorkoutsFragment)
    fun inject(mainWindowFragment: MainWindowFragment)
    fun inject(registrationFragment: RegistrationFragment)
//    fun inject(blank1Fragment: PlanFragment)
//    fun inject(blank2Fragment: Blank2Fragment)

    fun inject(performingExercisesFragment: PerformingExercisesFragment)
}
class MainApplication: DaggerApplication() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
//        appComponent = DaggerAppComponent.builder()
//            .appModule(this)
//            .build()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.factory().create(this)
    }

}

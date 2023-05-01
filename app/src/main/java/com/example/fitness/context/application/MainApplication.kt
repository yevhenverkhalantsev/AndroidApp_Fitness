package com.example.fitness.context.application

import Blank2Fragment
import android.app.Application
import com.example.fitness.context.injection.AppModule
import com.example.fitness.main_window.MainWindowFragment
import com.example.fitness.registration.RegistrationFragment
import com.example.fitness.retrofit.ApiModule
import com.example.fitness.test1.Blank1Fragment
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [ApiModule::class, AppModule::class])
interface AppComponent {
    fun inject(mainWindowFragment: MainWindowFragment)
    fun inject(registrationFragment: RegistrationFragment)
//    fun inject(blank1Fragment: Blank1Fragment)
//    fun inject(blank2Fragment: Blank2Fragment)
}
class MainApplication: Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

}

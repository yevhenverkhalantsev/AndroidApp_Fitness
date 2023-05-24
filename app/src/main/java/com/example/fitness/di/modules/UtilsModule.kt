package com.example.fitness.di.modules

import com.example.fitness.utils.ExerciseManager
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface UtilsModule {
    @Binds
    @Singleton
    fun bindExerciseManager(exerciseManager: ExerciseManager): ExerciseManager
}
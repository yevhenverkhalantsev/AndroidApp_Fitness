package com.example.fitness.screens.exercises.workouts.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitness.domain.repository.RemoteDatabaseRepository
import com.example.fitness.screens.exercises.workouts.model.Category
import com.example.fitness.utils.ExerciseManager
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExercisesViewModel @Inject constructor (
    private val remoteDatabaseRepository: RemoteDatabaseRepository,
    private val exerciseManager: ExerciseManager) : ViewModel() {

    fun saveUserProgram() {
        viewModelScope.launch {
            val exercises = exerciseManager.getDatabaseExercises(selectedCategory)
        }
    }

    lateinit var selectedCategory: Category


}

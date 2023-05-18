package com.example.fitness.screens.exercises.workouts.viewmodel

import androidx.lifecycle.ViewModel
import com.example.fitness.screens.exercises.workouts.model.Category
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExercisesViewModel @Inject constructor(): ViewModel() {
    lateinit var selectedCategory: Category
}
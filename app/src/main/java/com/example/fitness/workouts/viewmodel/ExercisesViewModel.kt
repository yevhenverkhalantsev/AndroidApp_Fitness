package com.example.fitness.workouts.viewmodel

import androidx.lifecycle.ViewModel
import com.example.fitness.workouts.model.Category
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExercisesViewModel @Inject constructor(): ViewModel() {
    lateinit var selectedCategory: Category
}
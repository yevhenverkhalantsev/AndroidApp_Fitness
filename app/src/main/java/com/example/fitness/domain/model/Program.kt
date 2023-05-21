package com.example.fitness.domain.model

import com.example.fitness.screens.exercises.exercises.model.DatabaseExercise

data class Program(
    val name: String,
    val exercises: List<DatabaseExercise>
) {
}
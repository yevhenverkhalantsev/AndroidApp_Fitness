package com.example.fitness.screens.exercises.exercises.model

import com.example.fitness.screens.exercises.exercisePerforming.model.ExerciseInterval

data class Exercise(
        val exercise_image: Int,
        val exercise_name: String,
    )

data class UserExerciseUI(
    val name: String,
    val imageId: Int,
    val description: String,
    val exerciseInterval: ExerciseInterval
)
package com.example.fitness.screens.exercises.exercises.model

import android.graphics.drawable.Drawable
import com.example.fitness.screens.exercises.exercisePerforming.model.ExerciseInterval

data class Exercise(
        val exercise_image: Drawable,
        val exercise_name: String,
    )

data class DatabaseExercise(
    val name: String,
    val imageId: Int,
    val description: String,
    val timeInteval: ExerciseInterval
)
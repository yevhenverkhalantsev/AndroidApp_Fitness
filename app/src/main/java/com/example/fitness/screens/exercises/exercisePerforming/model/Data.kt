package com.example.fitness.screens.exercises.exercisePerforming.model

import com.example.fitness.screens.exercises.exercises.model.Exercise

data class PerformingExercise(
    val exercise: Exercise,
    val time: ExerciseInterval
)

data class ExerciseInterval(
    val performingTime: Int,
    val restTime: Int
) {
    fun toStringTime(): String {
        return "$performingTime:$restTime"
    }

}
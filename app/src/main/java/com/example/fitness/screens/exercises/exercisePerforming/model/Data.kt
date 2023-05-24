package com.example.fitness.screens.exercises.exercisePerforming.model

import com.example.fitness.screens.exercises.someother.model.Exercise

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

    companion object {
        fun fromString(decodedInterval: String): ExerciseInterval {
            val times = decodedInterval.split(":")
            return ExerciseInterval(times[0].toInt(), times[1].toInt())
        }
    }
}
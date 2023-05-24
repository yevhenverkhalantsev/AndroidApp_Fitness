package com.example.fitness.domain.model

import com.example.fitness.screens.exercises.someother.model.UserExerciseUI

data class UserProgramUI(
    val name: String,
    val exercises: List<UserExerciseUI>,
    val icon: Int
)


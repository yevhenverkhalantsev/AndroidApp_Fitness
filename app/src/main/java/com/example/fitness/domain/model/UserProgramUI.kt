package com.example.fitness.domain.model

import com.example.fitness.screens.exercises.exercises.model.UserExerciseUI

data class UserProgramUI(
    val name: Int,
    val exercises: List<UserExerciseUI>,
    val icon: Int
)


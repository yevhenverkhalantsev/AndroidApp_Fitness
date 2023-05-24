package com.example.fitness.data.remotesource.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class UserProgramExercise(
    @Json(name = "id") val id : Int = 0,
    @Json(name = "user_program_id") val user_program_id : Int = 0,
    @Json(name = "user_exercise_id") val user_exercise_id : Int = 0
)
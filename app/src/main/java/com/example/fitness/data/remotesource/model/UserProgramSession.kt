package com.example.fitness.data.remotesource.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class UserProgramSession(
    @Json(name = "id") val id : Int = 0,
    @Json(name = "user_program_id") val user_program_id : Int,
    @Json(name = "startTime") val startTime : String
)
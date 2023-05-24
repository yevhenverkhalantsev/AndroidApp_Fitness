package com.example.fitness.data.remotesource.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserProgram(
    @Json(name = "id") val id: Int,
    @Json(name = "user_id") val user_id: Int,
    @Json(name = "app_program_type_id") val app_program_type_id: Int,
    @Json(name = "name") val name: String? = null,
    @Json(name = "use_timing") val use_timing: Int? = null,
    @Json(name = "icon") val icon: String? = null
)
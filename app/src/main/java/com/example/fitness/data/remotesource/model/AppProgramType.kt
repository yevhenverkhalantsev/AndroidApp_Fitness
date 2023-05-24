package com.example.fitness.data.remotesource.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AppProgramType(
    @Json(name = "id") val id: Int = 0,
    @Json(name = "description") val description: String? = null
)

package com.example.fitness.registration.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(
    @Json(name = "phone") val phone: String,
    @Json(name = "email") val email: String,
    @Json(name = "name") val name: String,
    @Json(name = "birth_year") val birth_year: Int
)
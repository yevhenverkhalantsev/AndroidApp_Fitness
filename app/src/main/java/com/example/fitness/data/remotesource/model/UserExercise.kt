package com.example.fitness.data.remotesource.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class UserExercise(
    @Json(name = "id") val id : Int = 0,
    @Json(name = "user_id") val user_id : Int = 0,
    @Json(name = "name") val name : String? = null,
    @Json(name = "description") val description : String? = null,
    @Json(name = "icon") val icon : String? = null,
    @Json(name = "photo_url") val photo_url : String? = null
)
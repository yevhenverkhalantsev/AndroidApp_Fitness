package com.example.fitness.registration.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(
    @Json(name = "id") val id: Int = 0,
    @Json(name = "phone") val phone: String,
    @Json(name = "email") val email: String? = null,
    @Json(name = "name") val name: String? = null,
    @Json(name = "birth_year") val birth_year: Int? = null
)

sealed class Resource {
    data class Success<T>(val element: T) : Resource()
    data class Error(val throwable: Throwable) : Resource()
    data class Loading(val inProcess: Boolean) : Resource()
}


package com.example.fitness.utils

import java.time.format.DateTimeFormatter

class Utils {
    companion object {
        const val userIdKey = "CurrentUserOnline"
        const val exerciseKeyID = "ExerciseID"
        val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    }
}


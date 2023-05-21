package com.example.fitness.data.remotesource.model

data class UserProgram(
    val id: Int,
    val user_id: Int,
    val app_program_type_id: Int,
    val name: String? = null,
    val use_timing: Int? = null,
    val icon: Int? = null
)
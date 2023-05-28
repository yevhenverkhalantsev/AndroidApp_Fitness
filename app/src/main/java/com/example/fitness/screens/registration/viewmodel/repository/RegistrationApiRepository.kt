package com.example.fitness.screens.registration.viewmodel.repository

import com.example.fitness.screens.registration.model.User
import com.example.fitness.retrofit.ApiService
import javax.inject.Inject

class RegistrationApiRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getUsers() = apiService.getUsers()
    @Throws(Throwable::class)
    suspend fun putUser(user: User) = apiService.putUser(user)

}

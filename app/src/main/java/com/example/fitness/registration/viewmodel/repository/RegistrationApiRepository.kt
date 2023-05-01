package com.example.fitness.registration.viewmodel.repository

import com.example.fitness.registration.model.User
import com.example.fitness.retrofit.ApiService
import javax.inject.Inject

class RegistrationApiRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getUsers() = apiService.getUsers()
    suspend fun putUser(user: User) = apiService.putUser(user)

//        suspend fun getPhotos() = apiService.getPhotos()

}

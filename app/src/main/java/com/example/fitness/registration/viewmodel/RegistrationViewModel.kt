package com.example.fitness.registration.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitness.registration.model.User
import com.example.fitness.registration.viewmodel.repository.RegistrationApiRepository
import kotlinx.coroutines.*
import javax.inject.Inject

class RegistrationViewModel @Inject constructor(private val repository: RegistrationApiRepository)
    : ViewModel(){

    fun getUsers() {
        viewModelScope.launch {
            delay(3000)
            val result = GlobalScope.async { repository.getUsers() }
            Log.i("kelo", "result = ${result.await()}")
        }
    }

    fun registrateUser(user: User) {
        val myJob = SupervisorJob()
        CoroutineScope(Dispatchers.IO + myJob).launch {
            try {
                CoroutineScope(Dispatchers.IO).launch {
                    repository.putUser(user)
                }
            } catch (E: Exception) {
                Log.i("kelo", "EXCEPTION!")
            }
        }
    }
}
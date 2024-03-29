package com.example.fitness.screens.registration.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitness.screens.registration.model.Resource
import com.example.fitness.screens.registration.model.User
import com.example.fitness.screens.registration.viewmodel.repository.RegistrationApiRepository
import kotlinx.coroutines.*
import javax.inject.Inject
class RegistrationViewModel @Inject constructor(private val repository: RegistrationApiRepository)
    : ViewModel(){

    private val _process = MutableLiveData<Resource>()
    val process get() = _process

    fun registrationUser(user: User) {
        _process.value = Resource.Loading(true)

        viewModelScope.launch {
            try {
            val result = withContext(Dispatchers.Default) {
                repository.putUser(user)
            }
                _process.value = Resource.Success(result)
            } catch (throwable: Throwable) {
                _process.value = Resource.Error(throwable)
            } finally {
                _process.value = Resource.Loading(false)
            }
        }
    }

}
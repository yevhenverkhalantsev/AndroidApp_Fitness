package com.example.fitness.login.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitness.domain.model.RemoteResource
import com.example.fitness.registration.model.Resource
import com.example.fitness.registration.viewmodel.repository.RegistrationApiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val repository: RegistrationApiRepository): ViewModel() {
    private val _loginResult = MutableLiveData<RemoteResource>()
    val loginResult: LiveData<RemoteResource> = _loginResult

    fun loginUser(phoneNumber: String) {
        _loginResult.value = RemoteResource.Loading(true)
        viewModelScope.launch {
            try {
                val users = withContext(Dispatchers.IO) { repository.getUsers() }
                val user = users.find { it.phone == phoneNumber }
                if (user != null) {
                    _loginResult.postValue(RemoteResource.Success(user))
                } else {
                    _loginResult.postValue(RemoteResource.OnConflict(""))
                }
            } catch (throwable: Throwable) {
                _loginResult.postValue(RemoteResource.Error(throwable))
            }
        }
    }


}

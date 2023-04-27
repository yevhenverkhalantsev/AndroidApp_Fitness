package com.example.fitness.registration.viewmodel

import androidx.lifecycle.ViewModel
import com.example.fitness.registration.viewmodel.repository.RegistrationApiRepository
import javax.inject.Inject

class RegistrationViewModel @Inject constructor(private val repository: RegistrationApiRepository) : ViewModel(){
}
package com.example.fitness.planfragment.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitness.data.remotesource.model.UserProgram
import com.example.fitness.data.remotesource.model.UserProgramSession
import com.example.fitness.domain.repository.PlanRemoteRepository
import com.example.fitness.utils.UserManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PlanViewModel @Inject constructor(
    private val planRemoteRepository: PlanRemoteRepository
): ViewModel() {
    private val _userPrograms = MutableLiveData<List<UserProgram>>()

    private val _userProgramSessions = MutableLiveData<List<UserProgramSession>>()

    init {
        viewModelScope.launch {
            val userPrograms = withContext(Dispatchers.IO) { planRemoteRepository.getUserPrograms(
                UserManager.userId) }
            _userPrograms.postValue(userPrograms)
            val userProgramsList = mutableListOf<UserProgramSession>()
            userPrograms.forEach {
                val userProgramSessions = withContext(Dispatchers.IO) { planRemoteRepository.getUserProgramSessions(it.id) }
                userProgramSessions.forEach { userProgramsList.add(it) }
            }
            _userProgramSessions.postValue(userProgramsList)
        }
    }

}
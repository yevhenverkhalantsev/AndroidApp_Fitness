package com.example.fitness.planfragment.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitness.data.remotesource.model.UserProgram
import com.example.fitness.data.remotesource.model.UserProgramSession
import com.example.fitness.domain.repository.PlanRemoteRepository
import com.example.fitness.registration.model.Resource
import com.example.fitness.utils.UserManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PlanViewModel @Inject constructor(
    private val planRemoteRepository: PlanRemoteRepository
): ViewModel() {
    fun getUserPrograms() {
        _userPrograms.postValue(Resource.Loading(true))
        _userProgramSessions.postValue(Resource.Loading(true))
        viewModelScope.launch {
            try {
                val userPrograms = withContext(Dispatchers.IO) {
                    planRemoteRepository.getUserPrograms(
                        UserManager.userId
                    )
                }
                _userPrograms.postValue(Resource.Success(userPrograms))
                getUserProgramSessions(userPrograms)
            } catch (throwable: Throwable) {
                _userPrograms.postValue(Resource.Error(throwable))
            }
        }
    }

    private fun getUserProgramSessions(userPrograms: List<UserProgram>) {
        viewModelScope.launch {
            val userProgramsList = mutableListOf<UserProgramSession>()
            Log.e("Kranbomin", "getUserProgramSessions: ${userPrograms.size}")
            val tmp = withContext(Dispatchers.IO) {
                userPrograms.forEach {
                    viewModelScope.launch {
                        try {
                            val userProgramSessions =
                                withContext(Dispatchers.IO) {
                                    planRemoteRepository.getUserProgramSessions(
                                        it.id
                                    )
                                }
                            userProgramSessions.forEach { userProgramsList.add(it) }
                        } catch (throwable: Throwable) {
                            //_userProgramSessions.postValue(Resource.Error(throwable))
                        }
                    }
                }
            }
            Log.e("Kranbomin", "getUserProgramSessions: ${userProgramsList.size}")
            _userProgramSessions.postValue(Resource.Success(userProgramsList))
            }
    }

    private val _userPrograms = MutableLiveData<Resource>()
    private val _userProgramSessions = MutableLiveData<Resource>()

    val userPrograms get() = _userPrograms as LiveData<Resource>
    val userProgramSessions get() = _userProgramSessions as LiveData<Resource>

    init {
        getUserPrograms()
    }

}
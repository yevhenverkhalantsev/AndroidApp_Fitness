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
import com.example.fitness.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class PlanViewModel @Inject constructor(
    private val planRemoteRepository: PlanRemoteRepository
): ViewModel() {
    private var currentUserProgramSessions: List<UserProgramSession> = listOf()
    private var currentUserPrograms: List<UserProgram> = listOf()
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
                currentUserPrograms = userPrograms
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
            withContext(Dispatchers.IO) {
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
            currentUserProgramSessions = userProgramsList
            _userProgramSessions.postValue(Resource.Success(userProgramsList))
            }
    }

    private fun getUserProgramSessionsByProgramId(userProgramId: Int): List<UserProgramSession>? {
        if (currentUserProgramSessions.isEmpty()) return null
        val userProgramSessionToDelete = mutableListOf<UserProgramSession>()
        currentUserProgramSessions.forEach {
            if (it.user_program_id == userProgramId) {
                userProgramSessionToDelete.add(it)
            }
        }
        if (userProgramSessionToDelete.isEmpty()) return null
        return userProgramSessionToDelete
    }

    fun deleteUserProgram(program: UserProgram) {
        viewModelScope.launch {
            val programSessionsToDelete = getUserProgramSessionsByProgramId(program.id)
            withContext(Dispatchers.IO) {
                withContext(Dispatchers.IO) {
                    programSessionsToDelete?.forEach {
                        planRemoteRepository.deleteUserProgramSession(it.id)
                    }
                }
                planRemoteRepository.deleteUserProgram(program.id)
            }
            getUserPrograms()
        }
    }

    fun saveUserProgramSession(selectedProgram: UserProgram, dateTime: LocalDateTime) {
        viewModelScope.launch {
            val userProgramSessionToUpdate = getUserProgramSessionByDateTime(dateTime)
            if (userProgramSessionToUpdate != null) {
                planRemoteRepository.updateUserProgramSession(
                    UserProgramSession(
                    id = userProgramSessionToUpdate.id,
                    user_program_id = selectedProgram.id,
                    startTime = userProgramSessionToUpdate.startTime))
            }
            else {
                withContext(Dispatchers.IO) {
                    planRemoteRepository.saveUserProgramSession(
                        UserProgramSession(
                            user_program_id = selectedProgram.id,
                            startTime = dateTime.format(Utils.dateTimeFormatter)
                        )
                    )
                }
            }
            getUserPrograms()
        }
    }

    private fun getUserProgramSessionByDateTime(dateTime: LocalDateTime): UserProgramSession? {
        currentUserProgramSessions.forEach {
            if (convertDBDateTimeToLocal(it.startTime).dayOfWeek == dateTime.dayOfWeek) {
                return it
            }
        }
        return null
    }

    fun convertDBDateTimeToLocal(startTime: String): LocalDateTime {
        val year = startTime.substring(0, 4).toInt()
        val month = startTime.substring(5, 7).toInt()
        val day = startTime.substring(8, 10).toInt()
        val result = LocalDateTime.of(year, month, day, 0, 0, 0)
        return result
    }

    private val _userPrograms = MutableLiveData<Resource>()
    private val _userProgramSessions = MutableLiveData<Resource>()

    val userPrograms get() = _userPrograms as LiveData<Resource>
    val userProgramSessions get() = _userProgramSessions as LiveData<Resource>

    init {
        getUserPrograms()
    }

}
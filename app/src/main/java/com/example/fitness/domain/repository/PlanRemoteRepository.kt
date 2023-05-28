package com.example.fitness.domain.repository

import com.example.fitness.data.remotesource.model.UserProgramSession
import com.example.fitness.retrofit.ApiService
import javax.inject.Inject

class PlanRemoteRepository @Inject constructor(
    private val apiService: ApiService) {

    suspend fun getUserPrograms(user_id: Int) = apiService.getUserProgram(user_id)
    suspend fun getUserProgramSessions(user_program_id: Int) =
        apiService.getUserProgramSession(user_program_id)
    suspend fun saveUserProgramSession(userProgramSession: UserProgramSession) =
        apiService.putUserProgramSession(userProgramSession)

    suspend fun deleteUserProgramSession(id: Int): Boolean {
        val response = apiService.deleteUserProgramSession(id)
        return response.isSuccessful
    }

    suspend fun deleteUserProgram(userProgramId: Int): Boolean {
        val response = apiService.deleteUserProgram(userProgramId)
        return response.isSuccessful
    }

    suspend fun updateUserProgramSession(userProgramSession: UserProgramSession) =
        apiService.updateUserProgramSession(userProgramSession)

}
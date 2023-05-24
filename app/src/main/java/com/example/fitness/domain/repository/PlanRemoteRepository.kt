package com.example.fitness.domain.repository

import com.example.fitness.data.remotesource.model.UserProgramSession
import com.example.fitness.retrofit.ApiService
import javax.inject.Inject

class PlanRemoteRepository @Inject constructor(
    private val apiService: ApiService) {

    suspend fun getUserExercises(user_id: Int) = apiService.getUserExercises(user_id)
    suspend fun getUserProgramExercises(user_program_id: Int) =
        apiService.getUserProgramExercises(user_program_id)
    suspend fun getUserPrograms(user_id: Int) = apiService.getUserProgram(user_id)
    suspend fun getUserProgramSessions(user_program_id: Int) =
        apiService.getUserProgramSession(user_program_id)
    suspend fun saveUserProgramSession(userProgramSession: UserProgramSession) =
        apiService.putUserProgramSession(userProgramSession)

}
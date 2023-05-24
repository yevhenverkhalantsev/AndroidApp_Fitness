package com.example.fitness.domain.repository

import com.example.fitness.data.remotesource.model.AppProgramType
import com.example.fitness.data.remotesource.model.UserExercise
import com.example.fitness.data.remotesource.model.UserProgram
import com.example.fitness.data.remotesource.model.UserProgramExercise
import com.example.fitness.retrofit.ApiService
import javax.inject.Inject

//@TODO ADD RETROFIT or as UseCase
class ExercisesRemoteRepository @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun saveAppProgramType(appProgramType: AppProgramType) =
        apiService.putAppProgramType(appProgramType)

    suspend fun getAllAppProgramTypes() = apiService.getAllAppProgramTypes()

    suspend fun saveUserProgram(userProgram: UserProgram) = apiService.putUserProgram(userProgram)
    suspend fun getUserProgram(user_id: Int) = apiService.getUserProgram(user_id)

    suspend fun saveUserExercise(userExercise: UserExercise) = apiService.putUserExercise(userExercise)

    suspend fun saveUserProgramExercise(userProgramExercise: UserProgramExercise) =
        apiService.putUserProgramExercise(userProgramExercise)
}
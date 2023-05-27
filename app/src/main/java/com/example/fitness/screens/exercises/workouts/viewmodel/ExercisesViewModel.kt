package com.example.fitness.screens.exercises.workouts.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitness.data.remotesource.model.AppProgramType
import com.example.fitness.data.remotesource.model.UserProgram
import com.example.fitness.data.remotesource.model.UserProgramExercise
import com.example.fitness.domain.model.RemoteResource
import com.example.fitness.domain.model.UserProgramUI
import com.example.fitness.domain.repository.ExercisesRemoteRepository
import com.example.fitness.screens.exercises.workouts.model.Category
import com.example.fitness.utils.ExerciseManager
import com.example.fitness.utils.Extensions.UiToDatabase.toUserExercise
import com.example.fitness.utils.Extensions.UiToDatabase.toUserProgram
import com.example.fitness.utils.UserManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ExercisesViewModel @Inject constructor (
    private val remoteDatabaseRepository: ExercisesRemoteRepository)
    : ViewModel() {

    lateinit var selectedCategory: Category

    private var _savingState = MutableLiveData<RemoteResource>()
    val savingState get() = _savingState as LiveData<RemoteResource>

    fun saveUserProgram(userProgramUI: UserProgramUI) {
        Log.e("WatchingMyDataHttp", "userProgramToSave = $userProgramUI")
        viewModelScope.launch {
            _savingState.postValue(RemoteResource.Loading(true))
            val userPrograms : List<UserProgram> = withContext(Dispatchers.IO) {
                try {
                    remoteDatabaseRepository.getUserProgram(UserManager.userId)
                } catch (throwable: Throwable) {
                    emptyList()
                }
            }
            userPrograms.forEach { userProgram ->
                if (userProgram.name == userProgramUI.name.toString()) {
                    _savingState.postValue(RemoteResource.OnConflict("You have already added this program!"))
                    return@launch
                }
            }

            var appProgramType: AppProgramType? = null
            val appProgramTypes = withContext(Dispatchers.IO) { remoteDatabaseRepository.getAllAppProgramTypes() }
            appProgramTypes.forEach { programType ->
                if (programType.description == userProgramUI.name.toString()) {
                    appProgramType = programType
                    return@forEach
                }
            }
            if (appProgramType == null) {
                appProgramType = withContext(Dispatchers.IO) {
                    remoteDatabaseRepository.saveAppProgramType(AppProgramType(
                        id = 0,
                        description = userProgramUI.name.toString()
                    ))
                }
            }

            val userProgram = withContext(Dispatchers.IO) {
                remoteDatabaseRepository.saveUserProgram(
                    userProgramUI.toUserProgram(UserManager.userId, appProgramType!!.id))
            }

            userProgramUI.exercises.forEach { exercise ->
                Log.i("RestTestingApi", "BEFORE saving userProgram!")

                val userExercise = withContext(Dispatchers.IO) {
                    remoteDatabaseRepository.saveUserExercise(
                        exercise.toUserExercise(UserManager.userId))
                }

                Log.i("RestTestingApi", "MIDDLE saving userProgram!")
                remoteDatabaseRepository.saveUserProgramExercise(
                    UserProgramExercise(
                        id = 0,
                        user_program_id = userProgram.id,
                        user_exercise_id = userExercise.id
                    )
                )
                _savingState.postValue(RemoteResource.Success(null))
                Log.i("RestTestingApi", "saving userProgram!")
            }
        }
    }

    fun clearState() {
        _savingState.value = RemoteResource.Loading(false)
    }


}

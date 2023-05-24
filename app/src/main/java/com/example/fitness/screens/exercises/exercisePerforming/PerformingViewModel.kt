package com.example.fitness.screens.exercises.exercisePerforming

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class PerformingViewModel @Inject constructor(): ViewModel() {
    private var _isProgramInProcess: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isProgramInProcess get() = _isProgramInProcess.asStateFlow()

    private var _restInProcess: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val restInProcess get() = _restInProcess.asStateFlow()


    fun startExercises(toStart: Boolean = false) {
        _isProgramInProcess.value = _isProgramInProcess.value == toStart
    }

    suspend fun startRest(toStart: Boolean = false) {
        _restInProcess.emit(toStart)
    }
}
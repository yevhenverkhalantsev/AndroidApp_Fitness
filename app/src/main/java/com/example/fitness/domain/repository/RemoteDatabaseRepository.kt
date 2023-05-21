package com.example.fitness.domain.repository

import com.example.fitness.domain.model.Program
import javax.inject.Inject

//@TODO ADD RETROFIT or as UseCase
class RemoteDatabaseRepository @Inject constructor() {

    fun saveUserProgram(program: Program) {
        //TODO Map to correct model
    }
}
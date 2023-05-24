package com.example.fitness.domain.model

sealed class RemoteResource {
    data class Success<T>(val element: T?) : RemoteResource()
    data class Error(val throwable: Throwable) : RemoteResource()
    data class Loading(val inProcess: Boolean) : RemoteResource()
    data class OnConflict(val message: String) : RemoteResource()
}
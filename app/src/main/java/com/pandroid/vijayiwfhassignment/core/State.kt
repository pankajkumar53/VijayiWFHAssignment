package com.pandroid.vijayiwfhassignment.core

sealed class State<out T> {
    data object Idle : State<Nothing>()
    data object Loading : State<Nothing>()
    data class Success<T>(val data: T) : State<T>()
    data class Error(val message: String?, val throwable: Throwable? = null) : State<Nothing>()
}
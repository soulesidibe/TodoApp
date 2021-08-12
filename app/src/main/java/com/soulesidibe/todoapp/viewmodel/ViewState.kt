package com.soulesidibe.todoapp.viewmodel

sealed class ViewState<T> {
    class Loading<T> : ViewState<T>()
    class Success<T>(val data: T) : ViewState<T>()
    class Failed<T>(val throwable: Throwable) : ViewState<T>()
    class Idle<T> : ViewState<T>()

    fun getDataOrNull(): T? {
        return try {
            (this as Success<T>).data
        } catch (e: Exception) {
            null
        }
    }

    companion object {
        fun <T> loading() = Loading<T>()
        fun <T> success(data: T) = Success(data)
        fun <T> failed(throwable: Throwable) = Failed<T>(throwable)
        fun <T> idle() = Idle<T>()
    }
}

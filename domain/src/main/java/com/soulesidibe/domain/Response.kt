package com.soulesidibe.domain

sealed class Response<T> {
    data class Success<T>(val data: T) : Response<T>()
    data class Error<T>(val throwable: Throwable) : Response<T>()

    companion object {
        fun <T> success(data: T) = Success(data)
        fun <T> failure(throwable: Throwable) = Error<T>(throwable)
    }
}


inline fun <R, T> Response<T>.map(transform: (value: T) -> R): Response<R> {
    return when(this) {
        is Response.Error -> Response.failure(this.throwable)
        is Response.Success -> Response.success(transform(this.data))
    }
}
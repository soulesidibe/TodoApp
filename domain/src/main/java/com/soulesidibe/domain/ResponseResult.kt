package com.soulesidibe.domain

import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

sealed class ResponseResult<T> {
    data class Success<T>(val data: T) : ResponseResult<T>()
    data class Error<T>(val throwable: Throwable) : ResponseResult<T>()

    companion object {
        fun <T> success(data: T) = Success(data)
        fun <T> failure(throwable: Throwable) = Error<T>(throwable)
    }
}


inline fun <R, T> ResponseResult<T>.map(transform: (value: T) -> R): ResponseResult<R> {
    return when(this) {
        is ResponseResult.Error -> ResponseResult.failure(this.throwable)
        is ResponseResult.Success -> ResponseResult.success(transform(this.data))
    }
}
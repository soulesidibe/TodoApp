package com.soulesidibe.todoapp.viewmodel

import com.soulesidibe.domain.ResponseResult

fun <T> ResponseResult<T>.toViewState(): ViewState<T> {
    return this.let {
        when (it) {
            is ResponseResult.Error -> {
                ViewState.failed(it.throwable)
            }
            is ResponseResult.Success -> {
                ViewState.success(it.data)
            }
        }
    }
}
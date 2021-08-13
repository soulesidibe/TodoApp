package com.soulesidibe.todoapp.viewmodel

import com.soulesidibe.domain.Response

fun <T> Response<T>.toViewState(): ViewState<T> {
    return this.let {
        when (it) {
            is Response.Error -> {
                ViewState.failed(it.throwable)
            }
            is Response.Success -> {
                ViewState.success(it.data)
            }
        }
    }
}
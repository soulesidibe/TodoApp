package com.soulesidibe.domain

interface UseCaseFlow<in Param, out Output> {

    suspend fun execute(param: Param): Output
}
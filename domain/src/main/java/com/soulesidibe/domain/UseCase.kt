package com.soulesidibe.domain

interface UseCase<in Param, out Output> {

    suspend fun execute(param: Param): Result<Output>
}
package com.soulesidibe.domain

interface UseCase<in Param, Output> {

    suspend fun execute(param: Param): ResponseResult<Output>
}
package com.soulesidibe.domain

interface UseCaseFlow<in Param, out Output> {

    fun execute(param: Param): Output
}
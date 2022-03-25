package com.soulesidibe.domain

import org.koin.dsl.module

val domainModule = module {
    factory { GetTodosUseCase(get()) }
    factory { GetTodoUseCase(get()) }
    factory { RemoveTodoUseCase(get()) }
    factory { AddOrUpdateTodoUseCase(get()) }
}
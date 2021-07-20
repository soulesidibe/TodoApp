package com.soulesidibe.data

import com.soulesidibe.data.repository.TodoRepositoryImpl
import com.soulesidibe.domain.repository.TodoRepository
import org.koin.dsl.module

val dataModule = module {
    single<TodoRepository> { TodoRepositoryImpl() }
}
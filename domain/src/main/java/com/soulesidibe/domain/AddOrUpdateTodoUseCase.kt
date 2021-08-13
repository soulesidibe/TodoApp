package com.soulesidibe.domain

import com.soulesidibe.domain.entity.TodoEntity
import com.soulesidibe.domain.repository.TodoRepository

class AddOrUpdateTodoUseCase(private val repository: TodoRepository) :
    UseCase<TodoEntity, Boolean> {
    override suspend fun execute(param: TodoEntity): Response<Boolean> {
        try {
            repository.addOrUpdate(param)
            return Response.success(true)
        } catch (e: Throwable) {
            return Response.failure(e)
        }
    }
}
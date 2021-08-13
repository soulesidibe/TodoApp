package com.soulesidibe.domain

import com.soulesidibe.domain.entity.TodoEntity
import com.soulesidibe.domain.exception.NoTodoFoundException
import com.soulesidibe.domain.repository.TodoRepository

class GetTodoUseCase(private val repository: TodoRepository) : UseCase<String, TodoEntity> {
    override suspend fun execute(param: String): Response<TodoEntity> {
        val value = repository.byId(param) ?: return Response.failure(NoTodoFoundException)
        return Response.success(value)
    }
}
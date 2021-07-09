package com.soulesidibe.domain

import com.soulesidibe.domain.entity.TodoEntity
import com.soulesidibe.domain.exception.NoTodoFoundException
import com.soulesidibe.domain.repository.TodoRepository
import java.lang.Exception

class GetTodoUseCase(private val repository: TodoRepository) : UseCase<String, TodoEntity> {
    override suspend fun execute(param: String): Result<TodoEntity> {
        val value = repository.byId(param) ?: return Result.failure(NoTodoFoundException())
        return Result.success(value)
    }
}
package com.soulesidibe.domain

import com.soulesidibe.domain.exception.NoTodoFoundException
import com.soulesidibe.domain.repository.TodoRepository

class RemoveTodoUseCase(private val repository: TodoRepository) : UseCase<String, Boolean> {
    override suspend fun execute(param: String): Result<Boolean> {
        val todo = repository.byId(param) ?: return Result.failure(NoTodoFoundException())
        return Result.success(repository.remove(todo))
    }
}
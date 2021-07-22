package com.soulesidibe.domain

import com.soulesidibe.domain.exception.NoTodoFoundException
import com.soulesidibe.domain.repository.TodoRepository

class RemoveTodoUseCase(private val repository: TodoRepository) : UseCase<String, Boolean> {
    override suspend fun execute(param: String): ResponseResult<Boolean> {
        val todo = repository.byId(param) ?: return ResponseResult.failure(NoTodoFoundException())
        return ResponseResult.success(repository.remove(todo))
    }
}
package com.soulesidibe.domain

import com.soulesidibe.domain.entity.TodoEntity
import com.soulesidibe.domain.exception.NoTodosFoundException
import com.soulesidibe.domain.repository.TodoRepository

class GetTodosUseCase(private val repository: TodoRepository) : UseCase<None, List<TodoEntity>> {
    override suspend fun execute(param: None): Result<List<TodoEntity>> {
        return try {
            Result.success(repository.get())
        } catch (e: NoTodosFoundException) {
            Result.failure(e)
        }
    }
}
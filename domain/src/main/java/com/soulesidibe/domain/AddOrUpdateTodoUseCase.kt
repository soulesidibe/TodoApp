package com.soulesidibe.domain

import com.soulesidibe.domain.entity.TodoEntity
import com.soulesidibe.domain.exception.NoTodoFoundException
import com.soulesidibe.domain.repository.TodoRepository
import java.lang.Exception

class AddOrUpdateTodoUseCase(private val repository: TodoRepository) :
    UseCase<TodoEntity, Boolean> {
    override suspend fun execute(param: TodoEntity): Result<Boolean> {
        try {
            if (repository.byId(param.id) != null) {
                return Result.success(repository.update(param))
            } else {
                return Result.success(repository.add(param))
            }
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}
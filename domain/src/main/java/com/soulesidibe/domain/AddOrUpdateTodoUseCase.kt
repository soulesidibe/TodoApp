package com.soulesidibe.domain

import com.soulesidibe.domain.entity.TodoEntity
import com.soulesidibe.domain.exception.NoTodoFoundException
import com.soulesidibe.domain.repository.TodoRepository
import java.lang.Exception

class AddOrUpdateTodoUseCase(private val repository: TodoRepository) :
    UseCase<TodoEntity, Boolean> {
    override suspend fun execute(param: TodoEntity): ResponseResult<Boolean> {
        try {
            repository.addOrUpdate(param)
            return ResponseResult.success(true)
        } catch (e: Throwable) {
            return ResponseResult.failure(e)
        }
    }
}
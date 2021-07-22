package com.soulesidibe.domain

import com.soulesidibe.domain.entity.TodoEntity
import com.soulesidibe.domain.exception.NoTodosFoundException
import com.soulesidibe.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow

class GetTodosUseCase(private val repository: TodoRepository) :
    UseCaseFlow<None, Flow<ResponseResult<List<TodoEntity>>>> {
    override suspend fun execute(param: None): Flow<ResponseResult<List<TodoEntity>>> {
        return repository.get()
    }
}
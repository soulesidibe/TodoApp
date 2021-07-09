package com.soulesidibe.domain

import com.soulesidibe.domain.entity.TodoEntity
import com.soulesidibe.domain.exception.NoTodosFoundException
import com.soulesidibe.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow

class GetTodosUseCase(private val repository: TodoRepository) :
    UseCaseFlow<None, Flow<List<TodoEntity>>> {
    override suspend fun execute(param: None): Flow<List<TodoEntity>> {
        return repository.get()
    }
}
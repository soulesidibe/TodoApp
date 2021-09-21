package com.soulesidibe.domain

import com.soulesidibe.domain.entity.TodoEntity
import com.soulesidibe.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow

class GetTodosUseCase(private val repository: TodoRepository) :
    UseCaseFlow<None, Flow<Response<List<TodoEntity>>>> {
    override fun execute(param: None): Flow<Response<List<TodoEntity>>> {
        return repository.get()
    }
}
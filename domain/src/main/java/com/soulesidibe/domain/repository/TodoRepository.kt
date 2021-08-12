package com.soulesidibe.domain.repository

import com.soulesidibe.domain.ResponseResult
import com.soulesidibe.domain.entity.TodoEntity
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    suspend fun get(): Flow<ResponseResult<List<TodoEntity>>>
    suspend fun byId(id: String): TodoEntity?
    suspend fun addOrUpdate(todoEntity: TodoEntity): Boolean
    suspend fun remove(todoEntity: TodoEntity): Boolean
}
package com.soulesidibe.domain.repository

import com.soulesidibe.domain.ResponseResult
import com.soulesidibe.domain.entity.TodoEntity
import com.soulesidibe.domain.exception.CannotAddOrUpdateException
import kotlinx.coroutines.flow.Flow
import kotlin.jvm.Throws

interface TodoRepository {

    suspend fun get(): Flow<ResponseResult<List<TodoEntity>>>

    suspend fun byId(id: String): TodoEntity?

    @Throws(CannotAddOrUpdateException::class)
    suspend fun addOrUpdate(todoEntity: TodoEntity): Boolean

    suspend fun remove(todoEntity: TodoEntity): Boolean
}
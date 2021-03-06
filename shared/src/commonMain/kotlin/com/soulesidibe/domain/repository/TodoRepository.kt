package com.soulesidibe.domain.repository

import com.soulesidibe.domain.Response
import com.soulesidibe.domain.entity.TodoEntity
import com.soulesidibe.domain.exception.CannotAddOrUpdateException
import com.soulesidibe.domain.exception.NoTodosFoundException
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.cancellation.CancellationException

interface TodoRepository {

    @Throws(NoTodosFoundException::class)
    fun get(): Flow<Response<List<TodoEntity>>>

    suspend fun byId(id: String): TodoEntity?

    @Throws(CannotAddOrUpdateException::class, CancellationException::class)
    suspend fun addOrUpdate(todoEntity: TodoEntity): Boolean

    suspend fun remove(todoEntity: TodoEntity): Boolean
}
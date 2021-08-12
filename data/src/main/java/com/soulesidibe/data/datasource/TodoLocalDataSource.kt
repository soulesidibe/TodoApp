package com.soulesidibe.data.datasource

import com.soulesidibe.data.model.TodoData
import kotlinx.coroutines.flow.Flow

interface TodoLocalDataSource {

    fun getAll(): Flow<List<TodoData>>

    suspend fun getBy(id: String): TodoData?

    suspend fun insert(todo: TodoData): Boolean

    suspend fun remove(todo: TodoData)
}
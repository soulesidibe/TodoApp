package com.soulesidibe.data.datasource

import com.soulesidibe.data.model.TodoData
import kotlinx.coroutines.flow.Flow
import java.sql.SQLException
import kotlin.jvm.Throws

interface TodoLocalDataSource {

    fun getAll(): Flow<List<TodoData>>

    suspend fun getBy(id: String): TodoData?

    @Throws(SQLException::class)
    suspend fun insert(todo: TodoData): Boolean

    @Throws(SQLException::class)
    suspend fun remove(todo: TodoData)
}
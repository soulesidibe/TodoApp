package com.soulesidibe.device.datasource

import com.soulesidibe.data.datasource.TodoLocalDataSource
import com.soulesidibe.data.model.TodoData
import com.soulesidibe.device.db.dao.TodoDao
import com.soulesidibe.device.model.TodoDb
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class TodoLocalDataSourceImpl(
    private val dao: TodoDao
) : TodoLocalDataSource {

    override fun getAll(): Flow<List<TodoData>> {
        return dao.getAll().map { todoDb -> todoDb.map { TodoData(it.id, it.title) } }
    }

    override suspend fun getBy(id: String): TodoData {
        return dao.getById(id).let { TodoData(it.id, it.title) }
    }

    override suspend fun insert(todo: TodoData): Boolean {
        return dao.insert(todo.let { TodoDb(it.id, it.title) }) > 0
    }

    override suspend fun remove(todo: TodoData) {
        dao.delete(todo.let { TodoDb(it.id, it.title) })
    }
}
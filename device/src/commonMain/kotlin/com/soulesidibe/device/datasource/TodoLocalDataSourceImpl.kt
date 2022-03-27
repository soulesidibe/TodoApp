package com.soulesidibe.device.datasource

import com.soulesidibe.data.datasource.TodoLocalDataSource
import com.soulesidibe.data.model.TodoData
import com.soulesidibe.device.sqldelight.TodoQueries
import com.squareup.sqldelight.runtime.coroutines.asFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

internal class TodoLocalDataSourceImpl(
    private val dao: TodoQueries
) : TodoLocalDataSource {

    override fun getAll(): Flow<List<TodoData>> {
        return dao.getAll().asFlow()
            .map { todoDb -> todoDb.executeAsList().map { TodoData(it.id, it.title) } }
    }

    override suspend fun getBy(id: String): TodoData? {
        return withContext(Dispatchers.Default) {
            dao.getById(id).executeAsOneOrNull()?.let { TodoData(it.id, it.title) }
        }
    }

    override suspend fun insert(todo: TodoData): Boolean {
        return withContext(Dispatchers.Default) {
            dao.insert(todo.id, todo.title)
            true
        }
    }

    override suspend fun remove(todo: TodoData) {
        withContext(Dispatchers.Default) {
            dao.delete(todo.id)
        }
    }
}
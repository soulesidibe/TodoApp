package com.soulesidibe.device.datasource

import com.soulesidibe.data.datasource.TodoLocalDataSource
import com.soulesidibe.data.model.TodoData
import com.soulesidibe.device.db.dao.TodoDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class TodoLocalDataSourceImpl(
    private val dao: TodoDao
) : TodoLocalDataSource {

    override fun getAll(): Flow<List<TodoData>> {
        return dao.getAll().map { todoDb -> todoDb.map { TodoData(it.id, it.title) } }
    }
}
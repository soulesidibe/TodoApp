package com.soulesidibe.data.datasource

import com.soulesidibe.data.model.TodoData
import com.soulesidibe.domain.entity.TodoEntity
import kotlinx.coroutines.flow.Flow

interface TodoLocalDataSource {

    fun getAll(): Flow<List<TodoData>>
}
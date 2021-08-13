package com.soulesidibe.data.repository

import com.soulesidibe.data.datasource.TodoLocalDataSource
import com.soulesidibe.data.model.TodoData
import com.soulesidibe.data.model.mapper.toDb
import com.soulesidibe.data.model.mapper.toEntity
import com.soulesidibe.domain.ResponseResult
import com.soulesidibe.domain.entity.TodoEntity
import com.soulesidibe.domain.exception.CannotAddOrUpdateException
import com.soulesidibe.domain.exception.NoTodosFoundException
import com.soulesidibe.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.sql.SQLException

internal class TodoRepositoryImpl(
    private val dataSource: TodoLocalDataSource
) : TodoRepository {

    private val transform: (TodoData) -> TodoEntity = { todoDb -> todoDb.toEntity() }

    override suspend fun get(): Flow<ResponseResult<List<TodoEntity>>> {
        return dataSource.getAll().map { list ->
            if (list.isEmpty()) {
                ResponseResult.failure(NoTodosFoundException)
            } else {
                ResponseResult.success(list.map(transform))
            }
        }
    }

    override suspend fun byId(id: String): TodoEntity? {
        return dataSource.getBy(id)?.toEntity()
    }

    override suspend fun addOrUpdate(todoEntity: TodoEntity): Boolean {
        try {
            return dataSource.insert(todoEntity.toDb())
        } catch (e: SQLException) {
            throw CannotAddOrUpdateException
        }

    }

    override suspend fun remove(todoEntity: TodoEntity): Boolean {
        dataSource.remove(todoEntity.toDb())
        return true
    }
}
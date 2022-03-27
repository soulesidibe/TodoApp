package com.soulesidibe.data.repository

import com.soulesidibe.data.datasource.TodoLocalDataSource
import com.soulesidibe.data.model.TodoData
import com.soulesidibe.data.model.mapper.toData
import com.soulesidibe.data.model.mapper.toEntity
import com.soulesidibe.domain.Response
import com.soulesidibe.domain.entity.TodoEntity
import com.soulesidibe.domain.exception.CannotAddOrUpdateException
import com.soulesidibe.domain.exception.CannotRemoveTodoException
import com.soulesidibe.domain.exception.NoTodosFoundException
import com.soulesidibe.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class TodoRepositoryImpl(
    private val dataSource: TodoLocalDataSource
) : TodoRepository {

    private val transform: (TodoData) -> TodoEntity = { todoDb -> todoDb.toEntity() }

    override fun get(): Flow<Response<List<TodoEntity>>> {
        return dataSource.getAll().map { list ->
            if (list.isEmpty()) {
                Response.failure(NoTodosFoundException)
            } else {
                Response.success(list.map(transform))
            }
        }
    }

    override suspend fun byId(id: String): TodoEntity? {
        return dataSource.getBy(id)?.toEntity()
    }

    override suspend fun addOrUpdate(todoEntity: TodoEntity): Boolean {
        try {
            val by = dataSource.getBy(todoEntity.id)
            if (by == null) {
                return dataSource.insert(todoEntity.toData())
            } else {
                return dataSource.update(todoEntity.toData())
            }
        } catch (e: Exception) {
            throw CannotAddOrUpdateException
        }

    }

    override suspend fun remove(todoEntity: TodoEntity): Boolean {
        try {
            dataSource.remove(todoEntity.toData())
            return true
        } catch (e: Exception) {
            throw CannotRemoveTodoException
        }
    }
}
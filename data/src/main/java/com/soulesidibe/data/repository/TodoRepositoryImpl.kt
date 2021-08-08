package com.soulesidibe.data.repository

import com.soulesidibe.data.datasource.TodoLocalDataSource
import com.soulesidibe.data.model.TodoData
import com.soulesidibe.data.model.mapper.toDb
import com.soulesidibe.data.model.mapper.toEntity
import com.soulesidibe.domain.ResponseResult
import com.soulesidibe.domain.entity.TodoEntity
import com.soulesidibe.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

internal class TodoRepositoryImpl(
    private val dataSource: TodoLocalDataSource
) : TodoRepository {

    private val database: MutableList<TodoData> = mutableListOf(
        TodoData(title = "Task 1"),
        TodoData(title = "Task 2"),
        TodoData(title = "Task 3"),
        TodoData(title = "Task 4"),
        TodoData(title = "Task 5"),
        TodoData(title = "Task 6"),
        TodoData(title = "Task 7"),
        TodoData(title = "Task 8"),
        TodoData(title = "Task 9"),
        TodoData(title = "Task 10"),
        TodoData(title = "Task 11"),
        TodoData(title = "Task 12"),
        TodoData(title = "Task 13"),
        TodoData(title = "Task 14"),
        TodoData(title = "Task 15"),
    )

    private lateinit var todos: MutableStateFlow<ResponseResult<List<TodoEntity>>>

    private val transform: (TodoData) -> TodoEntity = { todoDb -> todoDb.toEntity() }

    override suspend fun get(): Flow<ResponseResult<List<TodoEntity>>> {
        if (database.isEmpty()) {
            todos = MutableStateFlow(ResponseResult.failure(Exception()))
        } else {
            todos = MutableStateFlow(ResponseResult.success(database.map(transform)))
        }
        return todos
    }

    override suspend fun byId(id: String): TodoEntity? {
        return database.firstOrNull { it.id == id }?.toEntity()
    }

    override suspend fun add(todoEntity: TodoEntity): Boolean {
        database.add(todoEntity.toDb())
        todos.emit(ResponseResult.success(database.map(transform)))
        return true
    }

    override suspend fun update(todoEntity: TodoEntity): Boolean {
        val index = database.indexOfFirst { it.id == todoEntity.id }
        try {
            database[index] = todoEntity.toDb()
            todos.emit(ResponseResult.success(database.map(transform)))
            return true
        } catch (e: Exception) {
            return false
        }
    }

    override suspend fun remove(todoEntity: TodoEntity): Boolean {
        val result = database.removeAll { it.id == todoEntity.id }
        todos.emit(ResponseResult.success(database.map(transform)))
        return result
    }
}
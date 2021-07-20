package com.soulesidibe.data.repository

import com.soulesidibe.data.model.TodoDb
import com.soulesidibe.data.model.mapper.toDb
import com.soulesidibe.data.model.mapper.toEntity
import com.soulesidibe.domain.entity.TodoEntity
import com.soulesidibe.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

internal class TodoRepositoryImpl : TodoRepository {

    private val database: MutableList<TodoDb> = mutableListOf(
        TodoDb(title = "Task 1"),
        TodoDb(title = "Task 2"),
        TodoDb(title = "Task 3"),
        TodoDb(title = "Task 4"),
        TodoDb(title = "Task 5"),
        TodoDb(title = "Task 6"),
        TodoDb(title = "Task 7"),
        TodoDb(title = "Task 8"),
        TodoDb(title = "Task 9"),
        TodoDb(title = "Task 10"),
        TodoDb(title = "Task 11"),
        TodoDb(title = "Task 12"),
        TodoDb(title = "Task 13"),
        TodoDb(title = "Task 14"),
        TodoDb(title = "Task 15"),
    )

    private lateinit var todos: MutableStateFlow<List<TodoEntity>>

    private val transform: (TodoDb) -> TodoEntity = { todoDb -> todoDb.toEntity() }

    override suspend fun get(): Flow<List<TodoEntity>> {
        todos = MutableStateFlow(database.map(transform))
        return todos
    }

    override suspend fun byId(id: String): TodoEntity? {
        return database.firstOrNull { it.id == id }?.toEntity()
    }

    override suspend fun add(todoEntity: TodoEntity): Boolean {
        database.add(todoEntity.toDb())
        todos.emit(database.map(transform))
        return true
    }

    override suspend fun update(todoEntity: TodoEntity): Boolean {
        val index = database.indexOfFirst { it.id == todoEntity.id }
        try {
            database[index] = todoEntity.toDb()
            todos.emit(database.map(transform))
            return true
        } catch (e: Exception) {
            return false
        }
    }

    override suspend fun remove(todoEntity: TodoEntity): Boolean {
        val result = database.removeAll { it.id == todoEntity.id }
        todos.emit(database.map(transform))
        return result
    }
}
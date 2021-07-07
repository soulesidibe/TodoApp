package com.soulesidibe.data.repository

import com.soulesidibe.data.model.TodoDb
import com.soulesidibe.data.model.mapper.toDb
import com.soulesidibe.data.model.mapper.toEntity
import com.soulesidibe.domain.entity.TodoEntity
import com.soulesidibe.domain.exception.NoTodoFoundException
import com.soulesidibe.domain.exception.NoTodosFoundException
import com.soulesidibe.domain.repository.TodoRepository

class TodoRepositoryImpl : TodoRepository {

    private val database: MutableList<TodoDb> = mutableListOf()

    override suspend fun get(): List<TodoEntity> {
        if (database.isEmpty()) throw NoTodosFoundException()
        return database.map { it.toEntity() }
    }

    override suspend fun byId(id: String): TodoEntity {
        return database.firstOrNull { it.id == id }?.toEntity() ?: throw NoTodoFoundException()
    }

    override suspend fun add(todoEntity: TodoEntity): Boolean {
        database.add(todoEntity.toDb())
        return true
    }

    override suspend fun update(todoEntity: TodoEntity): Boolean {
        val index = database.indexOfFirst { it.id == todoEntity.id }
        try {
            database[index] = todoEntity.toDb()
            return true
        } catch (e: Exception) {
            return false
        }
    }

    override suspend fun remove(todoEntity: TodoEntity): Boolean {
        return database.removeAll { it.id == todoEntity.id }
    }
}
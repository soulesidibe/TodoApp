package com.soulesidibe.domain.repository

import com.soulesidibe.domain.entity.TodoEntity

interface TodoRepository {
    suspend fun get(): List<TodoEntity>
    suspend fun byId(id: String): TodoEntity
    suspend fun add(todoEntity: TodoEntity): Boolean
    suspend fun update(todoEntity: TodoEntity): Boolean
    suspend fun remove(todoEntity: TodoEntity): Boolean
}
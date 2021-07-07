package com.soulesidibe.data.model.mapper

import com.soulesidibe.data.model.TodoDb
import com.soulesidibe.domain.entity.TodoEntity

fun TodoEntity.toDb(): TodoDb {
    return TodoDb(this.id, this.title)
}

fun TodoDb.toEntity(): TodoEntity {
    return TodoEntity(id, title)
}
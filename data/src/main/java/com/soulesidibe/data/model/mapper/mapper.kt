package com.soulesidibe.data.model.mapper

import com.soulesidibe.data.model.TodoDb
import com.soulesidibe.domain.entity.TodoEntity

internal fun TodoEntity.toDb(): TodoDb {
    return TodoDb(this.id, this.title)
}

internal fun TodoDb.toEntity(): TodoEntity {
    return TodoEntity(id, title)
}
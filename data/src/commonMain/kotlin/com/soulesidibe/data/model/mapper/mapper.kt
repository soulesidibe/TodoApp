package com.soulesidibe.data.model.mapper

import com.soulesidibe.data.model.TodoData
import com.soulesidibe.domain.entity.TodoEntity

internal fun TodoEntity.toData(): TodoData {
    return TodoData(this.id, this.title)
}

internal fun TodoData.toEntity(): TodoEntity {
    return TodoEntity(id, title)
}
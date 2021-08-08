package com.soulesidibe.device.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todos")
data class TodoDb(
    @PrimaryKey val id: String,
    val title: String
)

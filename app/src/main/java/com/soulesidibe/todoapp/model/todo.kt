package com.soulesidibe.todoapp.model

import com.soulesidibe.domain.entity.TodoEntity
import java.util.*

data class TodoViewModel(val id: String = "${System.currentTimeMillis()}", val title: String)

fun TodoViewModel.toEntity(): TodoEntity = TodoEntity(id, title)

fun TodoEntity.toTodoViewModel(): TodoViewModel = TodoViewModel(id, title)
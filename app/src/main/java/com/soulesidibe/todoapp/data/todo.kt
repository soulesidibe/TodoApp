package com.soulesidibe.todocompose.data

import java.util.*

data class Todo(val id: String = UUID.randomUUID().toString(), val title: String)
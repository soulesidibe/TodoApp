package com.soulesidibe.domain.entity

import java.util.*

data class TodoEntity(val id: String = UUID.randomUUID().toString(), val title: String)

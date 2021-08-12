package com.soulesidibe.domain.entity

data class TodoEntity(val id: String = "${System.currentTimeMillis()}", val title: String)

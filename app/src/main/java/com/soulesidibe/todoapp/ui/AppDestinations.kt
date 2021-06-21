package com.soulesidibe.todoapp.ui

sealed class Screen(val route: String) {
    object Todos: Screen("todos")
    object Create: Screen("todos/{id}") {
        fun createRoute(id: String) = "todos/${id}"
    }
}

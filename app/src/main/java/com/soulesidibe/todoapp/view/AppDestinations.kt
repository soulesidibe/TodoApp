package com.soulesidibe.todoapp.view

sealed class Screen(val route: String) {
    object TodosScreen: Screen("todos")
    object CreateTodoScreen: Screen("todos/{id}") {
        fun createRoute(id: String) = "todos/${id}"
    }
}

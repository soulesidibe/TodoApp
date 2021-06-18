package com.soulesidibe.todoapp.ui

sealed class Screen(val route: String) {
    object Todos: Screen("todos")
    object Create: Screen("create")
}
package com.soulesidibe.todoapp.viewmodel

import kotlinx.coroutines.Dispatchers

class TodoCoroutineDispatcher {
    fun io() = Dispatchers.IO
    fun main() = Dispatchers.Main
    fun default() = Dispatchers.Default
}
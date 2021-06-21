package com.soulesidibe.todoapp.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.soulesidibe.todocompose.data.Todo

class TodoViewModel : ViewModel() {
    fun get(id: String?): Todo? {
        return id?.let {
            Todo(title = "Test 1")
        }
    }

    private val _todos: MutableLiveData<List<Todo>> = MutableLiveData(
        listOf(
            Todo(title = "Test 1"),
            Todo(title = "Test 2"),
            Todo(title = "Test 3"),
            Todo(title = "Test 4"),
        )
    )
    val todosLiveData = _todos
}
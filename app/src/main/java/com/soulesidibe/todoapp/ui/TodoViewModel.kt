package com.soulesidibe.todoapp.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.soulesidibe.todocompose.data.Todo

class TodoViewModel : ViewModel() {

    private val todos: MutableList<Todo> = mutableListOf()

    fun get(id: String?): Todo? {
        return id?.let { todoId ->
            val first = todos.firstOrNull {
                todoId == it.id
            }

            first
        }
    }

    fun add(todo: Todo) {
        todos.add(todo)
        _todos.postValue(todos)
    }

    private val _todos: MutableLiveData<List<Todo>> = MutableLiveData(todos)
    val todosLiveData = _todos
}
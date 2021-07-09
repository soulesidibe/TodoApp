package com.soulesidibe.todoapp.viewmodel

import androidx.lifecycle.*
import com.soulesidibe.domain.GetTodoUseCase
import com.soulesidibe.domain.GetTodosUseCase
import com.soulesidibe.domain.None
import com.soulesidibe.domain.entity.TodoEntity
import com.soulesidibe.domain.exception.NoTodosFoundException
import com.soulesidibe.todoapp.model.TodoViewModel
import com.soulesidibe.todoapp.model.toTodoViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.lang.Exception

class TodoListViewModel(
    private val useCase: GetTodosUseCase,
    private val dispatcher: TodoCoroutineDispatcher
) : ViewModel() {

    private val _todos: MutableLiveData<List<TodoViewModel>> = MutableLiveData()
    val todosLiveData: LiveData<List<TodoViewModel>> = _todos

    init {
        viewModelScope.launch(dispatcher.io()) {
            try {
                useCase.execute(None())
                    .map { entities ->
                        entities.map { it.toTodoViewModel() }
                    }.flowOn(dispatcher.io())
                    .catch {
                        emit(listOf())
                    }
                    .collect {
                        _todos.postValue(it)
                    }
            } catch (exception: Exception) {
                _todos.postValue(listOf())
            }

        }
    }

    fun get(id: String?): TodoViewModel? {
        return _todos.value?.firstOrNull { it.id == id }
    }
//
//    fun add(todoViewModel: TodoViewModel) {
//        var exist = false
//        val newList = todos.map { _todo ->
//            if (_todo.id == todoViewModel.id) {
//                exist = true
//                todoViewModel
//            } else _todo
//        }
//        todos.clear()
//        todos.addAll(newList)
//        if (!exist) {
//            todos.add(todoViewModel)
//        }
//        _todos.postValue(todos)
//    }
//
//    fun remove(id: String) {
//        todos.removeAll {
//            it.id == id
//        }
//    }

}
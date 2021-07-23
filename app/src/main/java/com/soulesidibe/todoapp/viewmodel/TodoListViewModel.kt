package com.soulesidibe.todoapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soulesidibe.domain.GetTodosUseCase
import com.soulesidibe.domain.None
import com.soulesidibe.domain.ResponseResult
import com.soulesidibe.domain.map
import com.soulesidibe.todoapp.model.TodoViewModel
import com.soulesidibe.todoapp.model.toTodoViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class TodoListViewModel(
    private val useCase: GetTodosUseCase,
    private val dispatcher: TodoCoroutineDispatcher
) : ViewModel() {

    private val _todos: MutableLiveData<ViewState<List<TodoViewModel>>> = MutableLiveData()
    val todosLiveData: LiveData<ViewState<List<TodoViewModel>>> = _todos

    init {
        viewModelScope.launch(dispatcher.io()) {
            _todos.postValue(ViewState.loading())
            try {
                useCase.execute(None())
                    .map { responseResult ->
                        responseResult.map { todos ->
                            todos.map { it.toTodoViewModel() }
                        }
                    }.flowOn(dispatcher.io())
                    .catch {
                        emit(ResponseResult.failure(Exception()))
                    }
                    .collect {
                        _todos.postValue(it.toViewState())
                    }
            } catch (exception: Exception) {
                _todos.postValue(ViewState.failed(Exception()))
            }

        }
    }

    fun get(id: String?): TodoViewModel? {
        return (_todos.value as ViewState.Success).data.firstOrNull { it.id == id }
    }

}
package com.soulesidibe.todoapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soulesidibe.domain.GetTodosUseCase
import com.soulesidibe.domain.None
import com.soulesidibe.domain.ResponseResult
import com.soulesidibe.domain.map
import com.soulesidibe.todoapp.model.TodoViewModel
import com.soulesidibe.todoapp.model.toTodoViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class TodoListViewModel(
    private val useCase: GetTodosUseCase,
    private val dispatcher: TodoCoroutineDispatcher
) : ViewModel() {

    private val _todosState: MutableStateFlow<ViewState<List<TodoViewModel>>> = MutableStateFlow(ViewState.idle())
    val todosState: StateFlow<ViewState<List<TodoViewModel>>> = _todosState

    init {
        viewModelScope.launch(dispatcher.io()) {
            _todosState.emit(ViewState.loading())
            delay(3000)
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
                        _todosState.emit(it.toViewState())
                    }
            } catch (exception: Throwable) {
                _todosState.emit(ViewState.failed(Exception()))
            }

        }
    }

    fun get(id: String?): TodoViewModel? {
        return (_todosState.value as ViewState.Success).data.firstOrNull { it.id == id }
    }

}
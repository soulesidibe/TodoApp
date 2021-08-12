package com.soulesidibe.todoapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soulesidibe.domain.AddOrUpdateTodoUseCase
import com.soulesidibe.domain.GetTodoUseCase
import com.soulesidibe.domain.RemoveTodoUseCase
import com.soulesidibe.domain.ResponseResult
import com.soulesidibe.todoapp.model.TodoViewModel
import com.soulesidibe.todoapp.model.toEntity
import com.soulesidibe.todoapp.model.toTodoViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class TodoDetailViewModel(
    private val useCase: AddOrUpdateTodoUseCase,
    private val removeUseCase: RemoveTodoUseCase,
    private val todoUseCase: GetTodoUseCase,
    private val dispatcher: TodoCoroutineDispatcher
) : ViewModel() {

    private val _addOrUpdate: MutableStateFlow<ViewState<Boolean>> =
        MutableStateFlow(ViewState.idle())
    val addOrUpdateState: StateFlow<ViewState<Boolean>> = _addOrUpdate.asStateFlow()

    private val _remove: MutableStateFlow<ViewState<Boolean>> = MutableStateFlow(ViewState.idle())
    val removeState: StateFlow<ViewState<Boolean>> = _remove

    fun addOrUpdate(todo: TodoViewModel) {
        viewModelScope.launch(dispatcher.io()) {
            _addOrUpdate.emit(ViewState.loading())
            val result: ViewState<Boolean> = useCase.execute(todo.toEntity()).toViewState()
            _addOrUpdate.emit(result)
        }
    }

    fun remove(id: String) {
        viewModelScope.launch(dispatcher.io()) {
            _remove.emit(ViewState.loading())
            val result: ViewState<Boolean> = removeUseCase.execute(id).toViewState()
            _remove.emit(result)
        }
    }

    fun getTodoBy(id: String) = flow<ViewState<TodoViewModel>> {
        when (val result = todoUseCase.execute(id)) {
            is ResponseResult.Error -> emit(ViewState.failed(result.throwable))
            is ResponseResult.Success -> emit(ViewState.success(result.data.toTodoViewModel()))
        }
    }.flowOn(dispatcher.io())
}
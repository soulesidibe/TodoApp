package com.soulesidibe.todoapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soulesidibe.domain.AddOrUpdateTodoUseCase
import com.soulesidibe.domain.RemoveTodoUseCase
import com.soulesidibe.todoapp.model.TodoViewModel
import com.soulesidibe.todoapp.model.toEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TodoDetailViewModel(
    private val useCase: AddOrUpdateTodoUseCase,
    private val removeUseCase: RemoveTodoUseCase,
    private val dispatcher: TodoCoroutineDispatcher
) : ViewModel() {

    private val _addOrUpdate: MutableStateFlow<ViewState<Boolean>> = MutableStateFlow(ViewState.idle())
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
}
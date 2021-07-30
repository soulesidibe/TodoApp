package com.soulesidibe.todoapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soulesidibe.domain.AddOrUpdateTodoUseCase
import com.soulesidibe.domain.RemoveTodoUseCase
import com.soulesidibe.domain.ResponseResult
import com.soulesidibe.todoapp.model.TodoViewModel
import com.soulesidibe.todoapp.model.toEntity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TodoDetailViewModel(
    private val useCase: AddOrUpdateTodoUseCase,
    private val removeUseCase: RemoveTodoUseCase,
    private val dispatcher: TodoCoroutineDispatcher
) : ViewModel() {

    private val _addOrUpdateLiveData: MutableLiveData<ViewState<Boolean>> = MutableLiveData()
    val addOrUpdateLiveData: LiveData<ViewState<Boolean>> = _addOrUpdateLiveData

    private val _removeLiveData: MutableLiveData<ViewState<Boolean>> = MutableLiveData()
    val removeLiveData: LiveData<ViewState<Boolean>> = _removeLiveData

    fun addOrUpdate(todo: TodoViewModel) {
        _addOrUpdateLiveData.postValue(ViewState.loading())
        viewModelScope.launch(dispatcher.io()) {
            val result: ViewState<Boolean> = useCase.execute(todo.toEntity()).toViewState()
            _addOrUpdateLiveData.postValue(result)
        }
    }

    fun remove(id: String) {
        _removeLiveData.postValue(ViewState.loading())
        viewModelScope.launch(dispatcher.io()) {
            val result: ViewState<Boolean> = removeUseCase.execute(id).toViewState()
            _removeLiveData.postValue(result)
        }
    }
}
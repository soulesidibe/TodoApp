package com.soulesidibe.todoapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soulesidibe.domain.AddOrUpdateTodoUseCase
import com.soulesidibe.domain.RemoveTodoUseCase
import com.soulesidibe.todoapp.model.TodoViewModel
import com.soulesidibe.todoapp.model.toEntity
import kotlinx.coroutines.launch

class TodoDetailViewModel(
    private val useCase: AddOrUpdateTodoUseCase,
    private val removeUseCase: RemoveTodoUseCase,
    private val dispatcher: TodoCoroutineDispatcher
) : ViewModel() {

    private val _addOrUpdateLiveData: MutableLiveData<Result<Boolean>> = MutableLiveData()
    val addOrUpdateLiveData: LiveData<Result<Boolean>> = _addOrUpdateLiveData

    private val _removeLiveData: MutableLiveData<Result<Boolean>> = MutableLiveData()
    val removeLiveData: LiveData<Result<Boolean>> = _removeLiveData

    fun addOrUpdate(todo: TodoViewModel) {
        viewModelScope.launch(dispatcher.io()) {
            val result: Result<Boolean> = useCase.execute(todo.toEntity())
            _addOrUpdateLiveData.postValue(result)
        }
    }

    fun remove(id: String) {
        viewModelScope.launch(dispatcher.io()) {
            val result: Result<Boolean> = removeUseCase.execute(id)
            _removeLiveData.postValue(result)
        }
    }
}
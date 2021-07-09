package com.soulesidibe.todoapp

import com.soulesidibe.data.repository.TodoRepositoryImpl
import com.soulesidibe.domain.AddOrUpdateTodoUseCase
import com.soulesidibe.domain.GetTodoUseCase
import com.soulesidibe.domain.GetTodosUseCase
import com.soulesidibe.domain.RemoveTodoUseCase
import com.soulesidibe.domain.repository.TodoRepository
import com.soulesidibe.todoapp.viewmodel.TodoCoroutineDispatcher
import com.soulesidibe.todoapp.viewmodel.TodoDetailViewModel
import com.soulesidibe.todoapp.viewmodel.TodoListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val module = module {
    single { TodoCoroutineDispatcher() }
    single<TodoRepository> { TodoRepositoryImpl() }
    factory { GetTodosUseCase(get()) }
    factory { GetTodoUseCase(get()) }
    factory { RemoveTodoUseCase(get()) }
    factory { AddOrUpdateTodoUseCase(get()) }
    viewModel { TodoDetailViewModel(get(), get(), get()) }
    viewModel { TodoListViewModel(get(), get()) }
}
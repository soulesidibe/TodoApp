package com.soulesidibe.todoapp

import com.soulesidibe.todoapp.viewmodel.TodoCoroutineDispatcher
import com.soulesidibe.todoapp.viewmodel.TodoDetailViewModel
import com.soulesidibe.todoapp.viewmodel.TodoListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { TodoCoroutineDispatcher() }
    viewModel { TodoDetailViewModel(get(), get(), get(), get()) }
    viewModel { TodoListViewModel(get(), get()) }
}
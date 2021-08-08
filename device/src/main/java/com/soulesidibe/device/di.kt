package com.soulesidibe.device

import androidx.room.Room
import com.soulesidibe.data.datasource.TodoLocalDataSource
import com.soulesidibe.device.datasource.TodoLocalDataSourceImpl
import com.soulesidibe.device.db.TodoDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


val deviceModule = module {

    single {
        Room.databaseBuilder(androidContext(), TodoDatabase::class.java, "todos_db")
            .build()
    }
    single { get<TodoDatabase>().getTodoDao() }
    single<TodoLocalDataSource> { TodoLocalDataSourceImpl(get()) }
}
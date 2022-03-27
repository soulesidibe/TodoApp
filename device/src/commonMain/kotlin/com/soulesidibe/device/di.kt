package com.soulesidibe.device

import com.soulesidibe.data.datasource.TodoLocalDataSource
import com.soulesidibe.device.datasource.TodoLocalDataSourceImpl
import com.soulesidibe.device.datasource.createDatabase
import org.koin.dsl.module

val deviceModule = module {
    single {
        createDatabase(get())
    }
    single { get<Database>().todoQueries }
    single<TodoLocalDataSource> { TodoLocalDataSourceImpl(get()) }
}
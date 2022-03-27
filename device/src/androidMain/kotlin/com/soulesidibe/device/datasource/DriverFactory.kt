package com.soulesidibe.device.datasource

import android.content.Context
import com.soulesidibe.device.Database
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.dsl.module

actual class DriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(Database.Schema, context, "todos_db")
    }
}

actual val dbModule: Module
    get() = module {
        single {
            DriverFactory(androidApplication())
        }
    }
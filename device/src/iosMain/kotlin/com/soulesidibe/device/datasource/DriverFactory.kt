package com.soulesidibe.device.datasource

import com.soulesidibe.device.Database
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import org.koin.core.module.Module
import org.koin.dsl.module

actual class DriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(Database.Schema, "todos_db")
    }
}

actual val dbModule: Module
    get() = module {
        single {
            DriverFactory()
        }
    }
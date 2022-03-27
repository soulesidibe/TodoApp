package com.soulesidibe.device.datasource

import com.soulesidibe.device.Database
import com.squareup.sqldelight.db.SqlDriver
import org.koin.core.module.Module

expect class DriverFactory {
    fun createDriver(): SqlDriver
}

fun createDatabase(driverFactory: DriverFactory): Database {
    val driver = driverFactory.createDriver()
    return Database(driver)
}

expect val dbModule: Module
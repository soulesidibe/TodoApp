package com.soulesidibe.todoapp

import android.app.Application
import com.soulesidibe.data.dataModule
import com.soulesidibe.device.deviceModule
import com.soulesidibe.domain.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class TodoApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@TodoApp)
            modules(appModule, dataModule, domainModule, deviceModule)
        }
    }
}
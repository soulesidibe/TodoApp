package com.soulesidibe.device.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.soulesidibe.device.db.dao.TodoDao
import com.soulesidibe.device.model.TodoDb

@Database(entities = [TodoDb::class], version = 1)
internal abstract class TodoDatabase: RoomDatabase() {

    abstract fun getTodoDao(): TodoDao
}
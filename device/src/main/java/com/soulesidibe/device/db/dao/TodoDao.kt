package com.soulesidibe.device.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.soulesidibe.device.model.TodoDb
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {

    @Query("select * from todos")
    fun getAll(): Flow<List<TodoDb>>

    @Insert
    suspend fun insert(todoDb: TodoDb): Long

    @Delete
    suspend fun delete(todoDb: TodoDb)
}
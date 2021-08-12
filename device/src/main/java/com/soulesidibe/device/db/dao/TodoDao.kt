package com.soulesidibe.device.db.dao

import androidx.room.*
import com.soulesidibe.device.model.TodoDb
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {

    @Query("select * from todos order by id")
    fun getAll(): Flow<List<TodoDb>>

    @Query("select * from todos where id=:todoId order by id desc")
    suspend fun getById(todoId: String): TodoDb?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(todoDb: TodoDb): Long

    @Delete
    suspend fun delete(todoDb: TodoDb)
}
package com.example.tasks.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.tasks.data.model.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks ORDER BY isDone, dateTime, priority DESC, id")
    fun allTasksSortTime(): Flow<List<Task>>

    @Query("SELECT * FROM tasks ORDER BY isDone, priority DESC, dateTime, id")
    fun allTasksSortPriority(): Flow<List<Task>>

    @Query("SELECT * FROM tasks WHERE dateTime >= :startDate AND dateTime < :endDate ORDER BY isDone, dateTime, priority DESC, id")
    fun dateTasksSortTime(startDate: String, endDate: String): Flow<List<Task>>

    @Query("SELECT * FROM tasks WHERE dateTime >= :startDate AND dateTime < :endDate ORDER BY isDone, priority DESC, datetime, id")
    fun dateTasksSortPriority(startDate: String, endDate: String): Flow<List<Task>>

    @Query("SELECT * FROM tasks WHERE title LIKE :query OR category LIKE :query ORDER BY isDone, dateTime, priority DESC, id")
    fun searchTasksSortTime(query: String): Flow<List<Task>>

    @Query("SELECT * FROM tasks WHERE title LIKE :query OR category LIKE :query ORDER BY isDone, priority DESC, dateTime, id")
    fun searchTasksSortPriority(query: String): Flow<List<Task>>

    @Insert
    suspend fun insertTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("DELETE FROM tasks")
    suspend fun deleteAllTasks()
}
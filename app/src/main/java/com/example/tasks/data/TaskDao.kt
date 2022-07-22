package com.example.tasks.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.tasks.data.model.Task

@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks ORDER BY isDone, dateTime, id")
    fun getAllTasks(): LiveData<List<Task>>

    @Query("SELECT * FROM tasks WHERE dateTime >= :startDate AND dateTime < :endDate ORDER BY isDone, dateTime, priority DESC, id")
    fun sortTasksByTime(startDate: String, endDate: String): LiveData<List<Task>>

    @Query("SELECT * FROM tasks WHERE dateTime >= :startDate AND dateTime < :endDate ORDER BY isDone, priority DESC, datetime, id")
    fun sortTasksByPriority(startDate: String, endDate: String): LiveData<List<Task>>

    @Insert
    suspend fun insertTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("DELETE FROM tasks")
    suspend fun deleteAllTasks()
}
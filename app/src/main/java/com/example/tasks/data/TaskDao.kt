package com.example.tasks.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.tasks.data.model.Task

@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks ORDER BY isDone, time, id")
    fun getAllTasks(): LiveData<List<Task>>

    @Insert
    suspend fun insertTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("DELETE FROM tasks")
    suspend fun deleteAllTasks()
}
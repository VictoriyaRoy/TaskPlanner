package com.example.tasks.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.tasks.data.model.Task

@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks")
    fun getAllTasks(): LiveData<List<Task>>

    @Insert
    fun insertTask(task: Task)

    @Update
    fun updateTask(task: Task)

    @Delete
    fun deleteTask(task: Task)

    @Query("DELETE FROM tasks")
    fun deleteAllTasks()
}
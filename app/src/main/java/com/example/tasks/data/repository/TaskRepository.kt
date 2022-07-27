package com.example.tasks.data.repository

import androidx.lifecycle.LiveData
import com.example.tasks.data.TaskDao
import com.example.tasks.data.model.Task
import kotlinx.coroutines.flow.Flow

class TaskRepository(private val taskDao: TaskDao) {
    fun allTasksSortTime(): Flow<List<Task>> = taskDao.allTasksSortTime()
    fun allTasksSortPriority(): Flow<List<Task>> = taskDao.allTasksSortPriority()

    fun dateTasksSortTime(startDate: String, endDate: String): Flow<List<Task>> =
        taskDao.dateTasksSortTime(startDate, endDate)

    fun dateTasksSortPriority(startDate: String, endDate: String): Flow<List<Task>> =
        taskDao.dateTasksSortPriority(startDate, endDate)

    fun searchTasksSortTime(query: String): Flow<List<Task>> =
        taskDao.searchTasksSortTime(query)

    fun searchTasksSortPriority(query: String): Flow<List<Task>> =
        taskDao.searchTasksSortPriority(query)

    suspend fun insertTask(task: Task) {
        taskDao.insertTask(task)
    }

    suspend fun updateTask(task: Task) {
        taskDao.updateTask(task)
    }

    suspend fun deleteTask(task: Task) {
        taskDao.deleteTask(task)
    }

    suspend fun deleteAllTasks() {
        taskDao.deleteAllTasks()
    }

}
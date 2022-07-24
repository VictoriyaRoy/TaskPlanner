package com.example.tasks.data.repository

import androidx.lifecycle.LiveData
import com.example.tasks.data.TaskDao
import com.example.tasks.data.model.Task
import kotlinx.coroutines.flow.Flow

class TaskRepository(private val taskDao: TaskDao) {
    val getAllTasks: LiveData<List<Task>> = taskDao.getAllTasks()

    fun sortTasksByTime(startDate: String, endDate: String): Flow<List<Task>> =
        taskDao.sortTasksByTime(startDate, endDate)

    fun sortTasksByPriority(startDate: String, endDate: String): Flow<List<Task>> =
        taskDao.sortTasksByPriority(startDate, endDate)

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
package com.example.tasks.data.repository

import androidx.lifecycle.LiveData
import com.example.tasks.data.TaskDao
import com.example.tasks.data.model.Task

class TaskRepository(private val taskDao: TaskDao) {
    val getAllTasks: LiveData<List<Task>> = taskDao.getAllTasks()

    fun getTasksByDate(startDate: String, endDate: String): LiveData<List<Task>> =
        taskDao.getTasksByDate(startDate, endDate)

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
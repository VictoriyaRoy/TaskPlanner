package com.example.tasks.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.tasks.data.TaskDatabase
import com.example.tasks.data.model.Task
import com.example.tasks.data.repository.TaskRepository
import com.example.tasks.utils.DateTimeUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.OffsetDateTime

class TaskViewModel(application: Application) : AndroidViewModel(application) {

    private val taskDao = TaskDatabase.getInstance(application).taskDao()
    private val repository = TaskRepository(taskDao)

    val getAllTasks: LiveData<List<Task>> = repository.getAllTasks
    fun getTasksByDate(date: OffsetDateTime): LiveData<List<Task>> {
        val startDate = DateTimeUtils.dateToTimestamp(date)
        val endDate = DateTimeUtils.dateToTimestamp(date.plusDays(1))
        return repository.getTasksByDate(startDate, endDate)
    }

    fun insertTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertTask(task)
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateTask(task)
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTask(task)
        }
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllTasks()
        }
    }
}
package com.example.tasks.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.tasks.data.TaskDatabase
import com.example.tasks.data.model.Sorting
import com.example.tasks.data.model.Task
import com.example.tasks.data.repository.TaskRepository
import com.example.tasks.utils.DateTimeUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.OffsetDateTime

class TaskViewModel(application: Application) : AndroidViewModel(application) {

    private val taskDao = TaskDatabase.getInstance(application).taskDao()
    private val repository = TaskRepository(taskDao)

    val getAllTasks: LiveData<List<Task>> = repository.getAllTasks
    fun getDayTasks(day: OffsetDateTime, sorting: Sorting): LiveData<List<Task>> {
        val startDate = DateTimeUtil.dateToTimestamp(day)
        val endDate = DateTimeUtil.dateToTimestamp(day.plusDays(1))
        return when(sorting) {
            Sorting.BY_TIME -> repository.sortTasksByTime(startDate, endDate)
            Sorting.BY_PRIORITY -> repository.sortTasksByPriority(startDate, endDate)
        }
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
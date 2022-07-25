package com.example.tasks.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.tasks.data.TaskDatabase
import com.example.tasks.data.model.Sorting
import com.example.tasks.data.model.Task
import com.example.tasks.data.repository.TaskRepository
import com.example.tasks.utils.DateTimeUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import java.time.OffsetDateTime

class DatabaseViewModel(application: Application) : AndroidViewModel(application) {

    private val taskDao = TaskDatabase.getInstance(application).taskDao()
    private val repository = TaskRepository(taskDao)

    private val sortFlow = MutableStateFlow(Pair(DateTimeUtil.todayStart, Sorting.defaultSort))

    @ExperimentalCoroutinesApi
    private val taskListFlow = sortFlow
        .flatMapLatest {
            val startDate = DateTimeUtil.dateToTimestamp(it.first)
            val endDate = DateTimeUtil.dateToTimestamp(it.first.plusDays(1))

            when (it.second) {
                Sorting.BY_TIME -> repository.sortTasksByTime(startDate, endDate)
                Sorting.BY_PRIORITY -> repository.sortTasksByPriority(startDate, endDate)
            }
        }

    @ExperimentalCoroutinesApi
    val taskList = taskListFlow.asLiveData()

    fun updateTaskList(day: OffsetDateTime, sorting: Sorting) {
        sortFlow.value = Pair(day, sorting)
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
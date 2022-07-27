package com.example.tasks.ui

import androidx.lifecycle.ViewModel
import com.example.tasks.data.model.Category
import com.example.tasks.data.model.Priority
import com.example.tasks.data.model.Task
import com.example.tasks.utils.DateTimeUtil
import kotlinx.coroutines.flow.MutableStateFlow

open class TaskViewModel : ViewModel() {
    var id = 0
    val title = MutableStateFlow("")
    val description = MutableStateFlow("")
    val dateTime = MutableStateFlow(DateTimeUtil.todayEnd)
    val category = MutableStateFlow(Category.NONE)
    val priority = MutableStateFlow(Priority.NONE)
    val isDone = MutableStateFlow(false)

    val activeDate = MutableStateFlow(false)

    fun buildTask() =
        Task(id,
            title.value,
            description.value,
            dateTime.value,
            category.value,
            priority.value,
            isDone.value)

    fun fromTask(task: Task) {
        id = task.id
        title.value = task.title
        description.value = task.description
        dateTime.value = task.dateTime
        category.value = task.category
        priority.value = task.priority
        isDone.value = task.isDone
    }

    fun setIsDone(checked: Boolean) {
        isDone.value = checked
    }
}

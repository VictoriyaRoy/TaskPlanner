package com.example.tasks.ui.dialogs

import android.content.Context
import androidx.fragment.app.FragmentManager
import com.example.tasks.data.model.Category
import com.example.tasks.data.model.Priority
import com.example.tasks.ui.TaskViewModel
import java.time.OffsetDateTime

class DialogManager(val taskVM: TaskViewModel) {

    fun chooseDateTime(context: Context) {
        val dateDialog = DateTimeDialog(context)
        dateDialog.dateTimeDialogListener = object : DateTimeDialog.DateTimeDialogListener {
            override fun onDateTimeSave(dateTime: OffsetDateTime) {
                taskVM.dateTime.value = dateTime
                taskVM.activeDate.value = true
            }
        }
        dateDialog.showDateTimeDialog(taskVM.dateTime.value)
    }

    fun chooseCategory(fragmentManager: FragmentManager) {
        val categoryDialog = CategoryDialog(taskVM.category.value)
        categoryDialog.categoryDialogListener =
            object : CategoryDialog.CategoryDialogListener {
                override fun onCategorySave(category: Category) {
                    taskVM.category.value = category
                }
            }
        categoryDialog.show(fragmentManager, CategoryDialog.TAG)
    }

    fun choosePriority(fragmentManager: FragmentManager) {
        val priorityDialog = PriorityDialog(taskVM.priority.value)
        priorityDialog.priorityDialogListener =
            object : PriorityDialog.PriorityDialogListener {
                override fun onPrioritySave(priority: Priority) {
                    taskVM.priority.value = priority
                }
            }
        priorityDialog.show(fragmentManager, PriorityDialog.TAG)
    }

    fun editTitle(fragmentManager: FragmentManager) {
        val titleDialog = TitleDialog(taskVM.title.value, taskVM.description.value)
        titleDialog.titleDialogListener =
            object : TitleDialog.TitleDialogListener {
                override fun onTitleSave(title: String, description: String) {
                    taskVM.title.value = title
                    taskVM.description.value = description
                }
            }
        titleDialog.show(fragmentManager, TitleDialog.TAG)
    }
}
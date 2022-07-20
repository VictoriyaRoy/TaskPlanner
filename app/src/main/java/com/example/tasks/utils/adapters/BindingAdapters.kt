package com.example.tasks.utils.adapters

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import com.example.tasks.data.model.Category
import com.example.tasks.data.model.Priority
import com.example.tasks.data.model.Task
import com.example.tasks.ui.list.ListFragmentDirections

class BindingAdapters {
    companion object {
        @BindingAdapter("android:hideIfNoCategory")
        @JvmStatic
        fun hideIfNoCategory(view: View, category: Category) {
            view.visibility = if (category == Category.NONE) View.GONE else View.VISIBLE
        }

        @BindingAdapter("android:hideIfNoPriority")
        @JvmStatic
        fun hideIfNoPriority(view: View, priority: Priority) {
            view.visibility = if (priority == Priority.NONE) View.GONE else View.VISIBLE
        }

        @BindingAdapter("android:hideIfEmpty")
        @JvmStatic
        fun hideIfEmpty(view: View, string: String) {
            view.visibility = if (string.isEmpty()) View.GONE else View.VISIBLE
        }

        @BindingAdapter("android:navigateToEdit")
        @JvmStatic
        fun navigateToEdit(view: View, task: Task) {
            view.setOnClickListener {
                val action = ListFragmentDirections.actionListFragmentToEditFragment(task)
                view.findNavController().navigate(action)
            }
        }
    }
}
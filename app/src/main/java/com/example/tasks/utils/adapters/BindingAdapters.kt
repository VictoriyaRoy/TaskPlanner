package com.example.tasks.utils.adapters

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.tasks.data.model.Category
import com.example.tasks.data.model.Priority

class BindingAdapters {
    companion object {
        @BindingAdapter("android:hideIfNoCategory")
        @JvmStatic
        fun hideIfNoCategory(view: TextView, category: Category) {
            view.visibility = if (category == Category.NONE) View.GONE else View.VISIBLE
        }

        @BindingAdapter("android:hideIfNoPriority")
        @JvmStatic
        fun hideIfNoPriority(view: TextView, priority: Priority) {
            view.visibility = if (priority == Priority.NONE) View.GONE else View.VISIBLE
        }
    }
}
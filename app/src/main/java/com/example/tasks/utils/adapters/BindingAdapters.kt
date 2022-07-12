package com.example.tasks.utils.adapters

import android.util.Log
import android.widget.CheckBox
import androidx.databinding.BindingAdapter
import com.example.tasks.data.model.Task

class BindingAdapters {
    companion object {
        @BindingAdapter("android:changeIsDone")
        @JvmStatic
        fun changeIsDone(view: CheckBox, task: Task) {
            view.setOnCheckedChangeListener { _, value ->
                task.isDone = value
            }
        }
    }
}
package com.example.tasks.utils.adapters

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter

class BindingAdapters {
    companion object {
        @BindingAdapter("android:hideIfNull")
        @JvmStatic
        fun hideIfNull(view: TextView, value: Any?) {
            view.visibility = if (value == null) View.GONE else View.VISIBLE
        }
    }
}
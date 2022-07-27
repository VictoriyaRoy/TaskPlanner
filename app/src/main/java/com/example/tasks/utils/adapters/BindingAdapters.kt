package com.example.tasks.utils.adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import com.example.tasks.R
import com.example.tasks.data.model.Category
import com.example.tasks.data.model.Priority
import com.example.tasks.data.model.Task
import com.example.tasks.ui.list.ListFragmentDirections
import com.example.tasks.utils.DateTimeUtil
import java.time.OffsetDateTime

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

        @BindingAdapter("android:hideView")
        @JvmStatic
        fun hideView(view: View, hide: Boolean) {
            view.visibility = if (hide) View.GONE else View.VISIBLE
        }

        @BindingAdapter("android:navigateToEdit")
        @JvmStatic
        fun navigateToEdit(view: View, task: Task) {
            view.setOnClickListener {
                val action = ListFragmentDirections.actionListFragmentToEditFragment(task)
                view.findNavController().navigate(action)
            }
        }

        @BindingAdapter("android:setCurrentDate")
        @JvmStatic
        fun setCurrentDate(view: TextView, date: OffsetDateTime) {
           view.text = DateTimeUtil.dateAsString(date, DateTimeUtil.FULL_FORMAT)
        }

        @BindingAdapter("android:setDateTime")
        @JvmStatic
        fun setDateTime(view: TextView, date: OffsetDateTime) {
            view.text = DateTimeUtil.dateTimeAsString(date)
        }

        @BindingAdapter("android:activeColor")
        @JvmStatic
        fun activeColor(view: ImageView, active: Boolean) {
            val context = view.context
            val color = if (active) R.color.accent_color else R.color.text_primary
            view.setColorFilter(context.getColor(color))
        }

        @BindingAdapter("android:doneColor")
        @JvmStatic
        fun doneColor(view: CardView, done: Boolean) {
            val context = view.context
            val color = if (done) R.color.secondary_color else R.color.primary_color
            view.setCardBackgroundColor(context.getColor(color))
        }
    }
}
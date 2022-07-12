package com.example.tasks.utils.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.tasks.data.model.Task

class TaskDiffUtil(
    private val oldList: List<Task>,
    private val newList: List<Task>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem.title == newItem.title &&
                oldItem.priority == newItem.priority &&
                oldItem.category == newItem.category &&
                oldItem.time == newItem.time
    }
}
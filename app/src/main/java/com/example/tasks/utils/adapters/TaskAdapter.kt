package com.example.tasks.utils.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.tasks.R
import com.example.tasks.data.model.Task
import com.example.tasks.databinding.TaskItemBinding

class TaskAdapter() : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {
    var taskList = emptyList<Task>()
        set(value) {
            val taskDiffUtil = TaskDiffUtil(taskList, value)
            val taskDiffResult = DiffUtil.calculateDiff(taskDiffUtil)
            field = value
            taskDiffResult.dispatchUpdatesTo(this)
        }

    class TaskViewHolder(private val binding: TaskItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(task: Task) {
            binding.task = task
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): TaskViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = TaskItemBinding.inflate(layoutInflater, parent, false)
                return TaskViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TaskViewHolder.from(parent)

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(taskList[position])
    }

    override fun getItemCount() = taskList.size
}
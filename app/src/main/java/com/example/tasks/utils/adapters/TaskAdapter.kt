package com.example.tasks.utils.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.tasks.data.model.Task
import com.example.tasks.databinding.TaskItemBinding

class TaskAdapter : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {
    var taskList = emptyList<Task>()
        set(value) {
            val taskDiffUtil = TaskDiffUtil(taskList, value)
            val taskDiffResult = DiffUtil.calculateDiff(taskDiffUtil)
            field = value
            taskDiffResult.dispatchUpdatesTo(this)
        }
    var onDoneChangeListener: OnDoneChangeListener? = null

    interface OnDoneChangeListener {
        fun onDoneChange(task: Task, isDone: Boolean)
    }

    inner class TaskViewHolder(private val binding: TaskItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(task: Task) {
            binding.task = task
            onDoneChangeListener?.let {
                binding.listener = it
            }
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = TaskItemBinding.inflate(layoutInflater, parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(taskList[position])
    }

    override fun getItemCount() = taskList.size
}
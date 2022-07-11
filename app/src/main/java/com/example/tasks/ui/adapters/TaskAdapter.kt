package com.example.tasks.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tasks.R
import com.example.tasks.data.model.Task

class TaskAdapter(private val taskList: List<Task>) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {
    inner class TaskViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val titleTv: TextView = itemView.findViewById(R.id.titleTv)
        val descriptionTv: TextView = itemView.findViewById(R.id.descriptionTv)
        val categoryTv: TextView = itemView.findViewById(R.id.categoryTv)
        val priorityTv: TextView = itemView.findViewById(R.id.priorityTv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)
        return TaskViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = taskList[position]
        with (holder) {
            titleTv.text = task.title
            descriptionTv.text = task.description
            categoryTv.text = task.category
            priorityTv.text = task.priority
        }
    }

    override fun getItemCount() = taskList.size
}
package com.example.tasks.ui.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tasks.data.model.Category
import com.example.tasks.data.model.Priority
import com.example.tasks.data.model.Task
import com.example.tasks.databinding.FragmentListBinding
import com.example.tasks.utils.adapters.TaskAdapter
import java.time.OffsetDateTime

class ListFragment : Fragment() {
    private val adapter: TaskAdapter by lazy { TaskAdapter() }

    private var _binding: FragmentListBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)

        val taskList = listOf(
            Task(0, "Clean house", OffsetDateTime.now().minusHours(1), Category.STUDY, Priority.HIGH, false),
            Task(1, "Feed cat", OffsetDateTime.now().plusDays(1), Category.WORK, Priority.MEDIUM, false),
            Task(1, "Feed cat", OffsetDateTime.now().minusMonths(3), Category.WORK, Priority.MEDIUM, false))
        adapter.taskList = taskList

        setupRecyclerView()

        return binding.root
    }

    private fun setupRecyclerView() {
        val recyclerView = binding.tasksRecycler
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
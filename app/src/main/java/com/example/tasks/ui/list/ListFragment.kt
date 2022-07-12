package com.example.tasks.ui.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tasks.data.model.Category
import com.example.tasks.data.model.Priority
import com.example.tasks.data.model.Task
import com.example.tasks.data.viewmodel.TaskViewModel
import com.example.tasks.databinding.FragmentListBinding
import com.example.tasks.utils.adapters.TaskAdapter
import java.time.OffsetDateTime

class ListFragment : Fragment() {
    private val adapter: TaskAdapter by lazy { TaskAdapter() }
    private val viewModel: TaskViewModel by viewModels()

    private var _binding: FragmentListBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)

        viewModel.getAllTasks.observe(viewLifecycleOwner) {
            adapter.taskList = it
        }

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
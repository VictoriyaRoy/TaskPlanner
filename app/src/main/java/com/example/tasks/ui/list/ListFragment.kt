package com.example.tasks.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tasks.data.model.Task
import com.example.tasks.data.viewmodel.TaskViewModel
import com.example.tasks.databinding.FragmentListBinding
import com.example.tasks.ui.add.AddFragment
import com.example.tasks.utils.adapters.TaskAdapter


class ListFragment : Fragment() {
    private val adapter: TaskAdapter by lazy { TaskAdapter() }
    private val viewModel: TaskViewModel by viewModels()

    private val addFragment: AddFragment by lazy { AddFragment() }

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

        binding.addTaskFab.setOnClickListener {
            addFragment.show(requireFragmentManager(), AddFragment.TAG)
        }

        adapter.onDoneChangeListener = object : TaskAdapter.OnDoneChangeListener {
            override fun onDoneChange(task: Task, isDone: Boolean) {
                task.isDone = isDone
                viewModel.updateTask(task)
            }
        }

        setupRecyclerView()

        return binding.root
    }

    private fun setupRecyclerView() {
        val recyclerView = binding.tasksRecycler
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
        swipeToDone(recyclerView)
    }

    private fun swipeToDone(recyclerView: RecyclerView) {
        val swipeToDoneCallback = object : SwipeToDone() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val task = adapter.taskList[viewHolder.adapterPosition]
                task.isDone = !task.isDone
                viewModel.updateTask(task)
                adapter.notifyItemChanged(viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDoneCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
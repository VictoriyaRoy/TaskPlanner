package com.example.tasks.ui.list

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tasks.R
import com.example.tasks.data.model.Task
import com.example.tasks.data.viewmodel.TaskViewModel
import com.example.tasks.databinding.FragmentListBinding
import com.example.tasks.ui.add.AddFragment
import com.example.tasks.ui.dialogs.DateTimeDialog
import com.example.tasks.utils.DateTimeUtil
import com.example.tasks.utils.adapters.TaskAdapter
import java.time.OffsetDateTime


class ListFragment : Fragment() {
    private val adapter: TaskAdapter by lazy { TaskAdapter() }
    private val viewModel: TaskViewModel by viewModels()

    private val addFragment: AddFragment by lazy { AddFragment() }
    private val timeDialog: DateTimeDialog by lazy { DateTimeDialog(requireContext()) }

    private lateinit var dayTvToolbar: TextView
    private var currentDate: OffsetDateTime = DateTimeUtil.startOfDay(OffsetDateTime.now())

    private var _binding: FragmentListBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)

        timeDialog.dateTimeDialogListener = object : DateTimeDialog.DateTimeDialogListener {
            override fun onDateTimeSave(dateTime: OffsetDateTime) {
                currentDate = dateTime
                updateDate()
            }
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
        setHasOptionsMenu(true)

        return binding.root
    }

    private fun updateDate() {
        dayTvToolbar.text = DateTimeUtil.dateAsString(currentDate)
        viewModel.getTasksByDate(currentDate).observe(viewLifecycleOwner) {
            adapter.taskList = it
        }
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.choose_date -> timeDialog.showOnlyDateDialog(currentDate)
            R.id.previous_day -> choosePreviousDay()
            R.id.next_day -> chooseNextDay()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun choosePreviousDay() {
        currentDate = currentDate.minusDays(1)
        updateDate()
    }

    private fun chooseNextDay() {
        currentDate = currentDate.plusDays(1)
        updateDate()
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        val alertMenuItem = menu.findItem(R.id.choose_date)
        val rootView = alertMenuItem.actionView as TextView

        dayTvToolbar = rootView.findViewById(R.id.day_tv_toolbar)
        updateDate()

        rootView.setOnClickListener {
            onOptionsItemSelected(alertMenuItem)
        }

        super.onPrepareOptionsMenu(menu)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
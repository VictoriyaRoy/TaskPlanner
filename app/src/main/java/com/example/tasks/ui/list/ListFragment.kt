package com.example.tasks.ui.list

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tasks.R
import com.example.tasks.data.model.Sorting
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
    private val args by navArgs<ListFragmentArgs>()
    val sharedPref: SharedPreferences? by lazy { activity?.getPreferences(Context.MODE_PRIVATE) }

    private val timeDialog: DateTimeDialog by lazy { DateTimeDialog(requireContext()) }

    private lateinit var currentDate: OffsetDateTime
    private lateinit var currentSorting: Sorting

    private var _binding: FragmentListBinding? = null
    private val binding
        get() = _binding!!

    companion object {
        private const val PAGE_DATE = "page_date"
        private const val SORTING_TYPE = "sorting_type"
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(PAGE_DATE, currentDate)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currentDate = args.pageDate?.let { DateTimeUtil.startOfDay(it) }
            ?: savedInstanceState?.let { it.getSerializable(PAGE_DATE) as OffsetDateTime }
                    ?: DateTimeUtil.todayDate
        currentSorting =
            Sorting.valueOf(sharedPref?.getString(SORTING_TYPE, Sorting.defaultName) ?: Sorting.defaultName)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        binding.currentDate = currentDate
        getDayTasks()

        timeDialog.dateTimeDialogListener = object : DateTimeDialog.DateTimeDialogListener {
            override fun onDateTimeSave(dateTime: OffsetDateTime) {
                updateDate(dateTime)
            }
        }

        binding.addTaskFab.setOnClickListener {
            val addFragment = AddFragment(currentDate.plusDays(1).minusHours(1))
            addFragment.addTaskListener = object : AddFragment.AddTaskListener {
                override fun onTaskAdd(task: Task) {
                    updateDate(DateTimeUtil.startOfDay(task.dateTime))
                }
            }
            addFragment.show(requireFragmentManager(), AddFragment.TAG)
        }

        adapter.onDoneChangeListener = object : TaskAdapter.OnDoneChangeListener {
            override fun onDoneChange(task: Task, isDone: Boolean) {
                task.isDone = isDone
                viewModel.updateTask(task)
            }
        }

        binding.chosenDateTv.setOnClickListener { chooseDayFromCalendar() }
        binding.previousDayBtn.setOnClickListener { choosePreviousDay() }
        binding.nextDayBtn.setOnClickListener { chooseNextDay() }

        setupRecyclerView()
        setHasOptionsMenu(true)

        return binding.root
    }

    private fun updateDate(newDate: OffsetDateTime) {
        currentDate = newDate
        binding.currentDate = newDate
        getDayTasks()
    }

    private fun changeSorting(sorting: Sorting) {
        currentSorting = sorting
        sharedPref?.let {
            with(it.edit()) {
                putString(SORTING_TYPE, sorting.name)
                apply()
            }
        }
        getDayTasks()
    }

    private fun getDayTasks() {
        viewModel.getDayTasks(currentDate, currentSorting).observe(viewLifecycleOwner) {
            adapter.taskList = it
        }
    }


    private fun setupRecyclerView() {
        val recyclerView = binding.tasksRecycler
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
    }

    private fun chooseDayFromCalendar() {
        timeDialog.showOnlyDateDialog(currentDate)
    }

    private fun choosePreviousDay() {
        updateDate(currentDate.minusDays(1))
    }

    private fun chooseNextDay() {
        updateDate(currentDate.plusDays(1))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu)
        val checkedItem = when(currentSorting) {
            Sorting.BY_TIME -> R.id.time_sort
            Sorting.BY_PRIORITY -> R.id.priority_sort
        }
        menu.findItem(checkedItem).isChecked = true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.isCheckable)
            item.isChecked = true

        when (item.itemId) {
            R.id.time_sort -> changeSorting(Sorting.BY_TIME)
            R.id.priority_sort -> changeSorting(Sorting.BY_PRIORITY)
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
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
import com.example.tasks.data.viewmodel.DatabaseViewModel
import com.example.tasks.databinding.FragmentListBinding
import com.example.tasks.ui.add.AddFragment
import com.example.tasks.ui.dialogs.DateTimeDialog
import com.example.tasks.utils.DateTimeUtil
import com.example.tasks.utils.adapters.TaskAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.time.OffsetDateTime

@ExperimentalCoroutinesApi
class ListFragment : Fragment(), ListEventHandler {
    private val taskVM: DatabaseViewModel by viewModels()
    private val listVM: ListViewModel by viewModels()

    private val args by navArgs<ListFragmentArgs>()
    private val sharedPref: SharedPreferences? by lazy { activity?.getPreferences(Context.MODE_PRIVATE) }

    private lateinit var sorting: Sorting

    private var _binding: FragmentListBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        args.pageDate?.let { listVM.setDate(DateTimeUtil.startOfDay(it)) }
        sorting = sharedPref?.let { Sorting.fromSharedPref(it) } ?: Sorting.defaultSort
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        binding.viewmodel = listVM
        binding.handler = this
        binding.lifecycleOwner = this

        listVM.date.observe(viewLifecycleOwner) {
            updateTaskList(it)
        }

        setupRecyclerView()
        setHasOptionsMenu(true)

        return binding.root
    }

    override fun showAddDialog() {
        val date = listVM.date.value ?: DateTimeUtil.todayStart
        val addFragment = AddFragment(DateTimeUtil.endOfDay(date))
        addFragment.addTaskListener = object : AddFragment.AddTaskListener {
            override fun onTaskAdd(task: Task) {
                listVM.setDate(DateTimeUtil.startOfDay(task.dateTime))
            }
        }
        addFragment.show(requireFragmentManager(), AddFragment.TAG)
    }

    override fun showCalendarDialog() {
        val date = listVM.date.value ?: DateTimeUtil.todayStart
        val timeDialog = DateTimeDialog(requireContext())
        timeDialog.dateTimeDialogListener = object : DateTimeDialog.DateTimeDialogListener {
            override fun onDateTimeSave(dateTime: OffsetDateTime) {
                listVM.setDate(dateTime)
            }
        }
        timeDialog.showOnlyDateDialog(date)
    }

    private fun updateTaskList(date: OffsetDateTime?) {
        date?.let { taskVM.updateTaskList(it, sorting) }
    }

    private fun setupRecyclerView() {
        val recyclerView = binding.tasksRecycler
        recyclerView.layoutManager = LinearLayoutManager(activity)

        val adapter = TaskAdapter()
        taskVM.taskList.observe(viewLifecycleOwner) {
            adapter.taskList = it
        }
        adapter.onDoneChangeListener = object : TaskAdapter.OnDoneChangeListener {
            override fun onDoneChange(task: Task, isDone: Boolean) {
                task.isDone = isDone
                taskVM.updateTask(task)
            }
        }
        recyclerView.adapter = adapter
    }

    private fun changeSorting(newSorting: Sorting) {
        sorting = newSorting
        sharedPref?.let {
            sorting.updateSharedPref(it)
        }
        updateTaskList(listVM.date.value)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu)
        val checkedItem = when (sorting) {
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
            else -> return false
        }
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
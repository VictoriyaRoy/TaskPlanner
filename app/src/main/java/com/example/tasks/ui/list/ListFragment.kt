package com.example.tasks.ui.list

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tasks.R
import com.example.tasks.data.model.Sorting
import com.example.tasks.data.model.Task
import com.example.tasks.data.viewmodel.DataViewModel
import com.example.tasks.databinding.FragmentListBinding
import com.example.tasks.ui.add.AddFragment
import com.example.tasks.ui.dialogs.DateTimeDialog
import com.example.tasks.utils.DateTimeUtil
import com.example.tasks.utils.adapters.TaskAdapter
import jp.wasabeef.recyclerview.animators.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.time.OffsetDateTime

@ExperimentalCoroutinesApi
class ListFragment : Fragment(), ListEventHandler {
    private val dataVM: DataViewModel by viewModels()
    private val listVM: ListViewModel by viewModels()

    private val args by navArgs<ListFragmentArgs>()
    private val sharedPref: SharedPreferences? by lazy { activity?.getPreferences(Context.MODE_PRIVATE) }

    private var _binding: FragmentListBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        args.pageDate?.let { listVM.setDate(DateTimeUtil.startOfDay(it)) }
        sharedPref?.let { listVM.setSorting(Sorting.fromSharedPref(it)) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        binding.viewmodel = listVM
        binding.handler = this
        binding.lifecycleOwner = this

        listVM.params.observe(viewLifecycleOwner) {
            dataVM.updateTaskList(it)
        }

        setupRecyclerView()
        setHasOptionsMenu(true)

        return binding.root
    }

    private fun changeSorting(newSorting: Sorting) {
        sharedPref?.let {
            newSorting.writeToSharedPref(it)
        }
        listVM.setSorting(newSorting)
    }

    override fun showAddDialog() {
        val date = listVM.params.value?.first ?: DateTimeUtil.todayStart
        val addFragment = AddFragment(DateTimeUtil.endOfDay(date))
        addFragment.addTaskListener = object : AddFragment.AddTaskListener {
            override fun onTaskAdd(task: Task) {
                listVM.setDate(DateTimeUtil.startOfDay(task.dateTime))
            }
        }
        addFragment.show(requireFragmentManager(), AddFragment.TAG)
    }

    override fun showCalendarDialog() {
        val date = listVM.params.value?.first ?: DateTimeUtil.todayStart
        val timeDialog = DateTimeDialog(requireContext())
        timeDialog.dateTimeDialogListener = object : DateTimeDialog.DateTimeDialogListener {
            override fun onDateTimeSave(dateTime: OffsetDateTime) {
                listVM.setDate(dateTime)
            }
        }
        timeDialog.showOnlyDateDialog(date)
    }

    private fun setupRecyclerView() {
        val recyclerView = binding.tasksRecycler
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.itemAnimator = FadeInUpAnimator()

        val adapter = TaskAdapter()
        dataVM.taskList.observe(viewLifecycleOwner) {
            adapter.taskList = it
        }
        adapter.onDoneChangeListener = object : TaskAdapter.OnDoneChangeListener {
            override fun onDoneChange(task: Task, isDone: Boolean) {
                task.isDone = isDone
                dataVM.updateTask(task)
            }
        }
        recyclerView.adapter = adapter
    }

    private fun setupSearchView(searchView: SearchView) {
        searchView.isSubmitButtonEnabled = false
        searchView.setOnSearchClickListener { listVM.setSearch("") }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { listVM.setSearch(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    if (listVM.params.value?.third != null)
                        listVM.setSearch(it)
                }
                return true
            }
        })

        searchView.setOnCloseListener {
            listVM.setSearch(null)
            return@setOnCloseListener false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu)
        listVM.params.value?.let {
            val checkedItem = when (it.second) {
                Sorting.BY_TIME -> R.id.time_sort
                Sorting.BY_PRIORITY -> R.id.priority_sort
            }
            menu.findItem(checkedItem).isChecked = true
        }

        val search = menu.findItem(R.id.menu_search)
        val searchView = search.actionView as? SearchView
        searchView?.let { setupSearchView(it) }
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
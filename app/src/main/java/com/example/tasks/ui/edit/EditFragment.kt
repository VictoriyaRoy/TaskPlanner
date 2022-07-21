package com.example.tasks.ui.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.tasks.R
import com.example.tasks.data.model.Category
import com.example.tasks.data.model.Priority
import com.example.tasks.data.model.Task
import com.example.tasks.data.viewmodel.TaskViewModel
import com.example.tasks.databinding.FragmentEditBinding
import com.example.tasks.ui.SharedViewModel
import com.example.tasks.ui.dialogs.CategoryDialog
import com.example.tasks.ui.dialogs.DateTimeDialog
import com.example.tasks.ui.dialogs.PriorityDialog
import com.example.tasks.ui.dialogs.TitleDialog
import java.time.OffsetDateTime


class EditFragment : Fragment() {
    private val taskViewModel: TaskViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by viewModels()
    private val args by navArgs<EditFragmentArgs>()

    private val currentTask: Task by lazy { args.task }
    private val timeDialog: DateTimeDialog by lazy { DateTimeDialog(requireContext()) }

    private var _binding: FragmentEditBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditBinding.inflate(inflater, container, false)
        updateTask()

        timeDialog.dateTimeDialogListener = object : DateTimeDialog.DateTimeDialogListener {
            override fun onDateTimeSave(dateTime: OffsetDateTime) {
                currentTask.dateTime = dateTime
                updateTask()
            }
        }

        binding.titleIconEdit.setOnClickListener { editTitle() }
        binding.timeTvEdit.setOnClickListener { chooseDateTime() }
        binding.categoryLabelEdit.setOnClickListener { chooseCategory() }
        binding.priorityLabelEdit.setOnClickListener { choosePriority() }
        binding.deleteTaskTv.setOnClickListener { deleteTask() }
        binding.saveChangesBtn.setOnClickListener { saveChanges() }

        binding.doneCheckEdit.setOnCheckedChangeListener { _, isDone ->
            currentTask.isDone = isDone
        }

        return binding.root
    }

    private fun updateTask() {
        binding.task = currentTask
    }

    private fun deleteTask() {
        taskViewModel.deleteTask(currentTask)
        sharedViewModel.showSuccessToast(currentTask.title, SharedViewModel.SUCCESS_DELETE_TASK)
        findNavController().navigate(R.id.action_editFragment_to_listFragment)
    }

    private fun saveChanges() {
        taskViewModel.updateTask(currentTask)
        sharedViewModel.showSuccessToast(currentTask.title, SharedViewModel.SUCCESS_SAVE_TASK)
        findNavController().navigate(R.id.action_editFragment_to_listFragment)
    }

    private fun chooseDateTime() {
        timeDialog.showDateTimeDialog(currentTask.dateTime)
    }

    private fun editTitle() {
        val myDialogFragment = TitleDialog(currentTask.title, currentTask.description)
        myDialogFragment.titleDialogListener =
            object : TitleDialog.TitleDialogListener {
                override fun onTitleSave(title: String, description: String) {
                    currentTask.title = title
                    currentTask.description = description
                    updateTask()
                }
            }
        myDialogFragment.show(parentFragmentManager, TitleDialog.TAG)
    }

    private fun chooseCategory() {
        val myDialogFragment = CategoryDialog(currentTask.category)
        myDialogFragment.categoryDialogListener =
            object : CategoryDialog.CategoryDialogListener {
                override fun onCategorySave(category: Category) {
                    currentTask.category = category
                    updateTask()
                }
            }
        myDialogFragment.show(parentFragmentManager, CategoryDialog.TAG)
    }

    private fun choosePriority() {
        val myDialogFragment = PriorityDialog(currentTask.priority)
        myDialogFragment.priorityDialogListener =
            object : PriorityDialog.PriorityDialogListener {
                override fun onPrioritySave(priority: Priority) {
                    currentTask.priority = priority
                    updateTask()
                }
            }
        myDialogFragment.show(parentFragmentManager, PriorityDialog.TAG)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
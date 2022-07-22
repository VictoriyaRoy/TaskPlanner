package com.example.tasks.ui.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.tasks.data.model.Category
import com.example.tasks.data.model.Priority
import com.example.tasks.data.model.Task
import com.example.tasks.data.viewmodel.TaskViewModel
import com.example.tasks.databinding.FragmentEditBinding
import com.example.tasks.ui.SharedViewModel
import com.example.tasks.ui.dialogs.*
import java.time.OffsetDateTime


class EditFragment : Fragment() {
    private val taskViewModel: TaskViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by viewModels()
    private val args by navArgs<EditFragmentArgs>()

    private val currentTask: Task by lazy { args.task }

    private var _binding: FragmentEditBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditBinding.inflate(inflater, container, false)
        updateTask()

        binding.titleIconEdit.setOnClickListener { editTitle() }
        binding.timeTvEdit.setOnClickListener { editDateTime() }
        binding.categoryLabelEdit.setOnClickListener { editCategory() }
        binding.priorityLabelEdit.setOnClickListener { editPriority() }
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

    private fun saveChanges() {
        taskViewModel.updateTask(currentTask)
        sharedViewModel.showSuccessToast(currentTask.title, SharedViewModel.SUCCESS_UPDATE_TASK)
        navigateToList()
    }

    private fun deleteTask() {
        val myDialogFragment = DeleteDialog(currentTask.title)
        myDialogFragment.deleteDialogListener =
            object : DeleteDialog.DeleteDialogListener {
                override fun onTaskDelete() {
                    taskViewModel.deleteTask(currentTask)
                    sharedViewModel.showSuccessToast(
                        currentTask.title,
                        SharedViewModel.SUCCESS_DELETE_TASK
                    )
                    navigateToList()
                }
            }
        myDialogFragment.show(parentFragmentManager, DeleteDialog.TAG)
    }

    private fun editDateTime() {
        val dialogFragment = DateTimeDialog(requireContext())
        dialogFragment.dateTimeDialogListener = object : DateTimeDialog.DateTimeDialogListener {
            override fun onDateTimeSave(dateTime: OffsetDateTime) {
                currentTask.dateTime = dateTime
                updateTask()
            }
        }
        dialogFragment.showDateTimeDialog(currentTask.dateTime)
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

    private fun editCategory() {
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

    private fun editPriority() {
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


    private fun navigateToList() {
        val action = EditFragmentDirections.actionEditFragmentToListFragment(currentTask.dateTime)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
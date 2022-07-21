package com.example.tasks.ui.add

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.viewModels
import com.example.tasks.R
import com.example.tasks.data.model.Category
import com.example.tasks.data.model.Priority
import com.example.tasks.data.model.Task
import com.example.tasks.data.viewmodel.TaskViewModel
import com.example.tasks.databinding.FragmentAddBinding
import com.example.tasks.ui.SharedViewModel
import com.example.tasks.ui.dialogs.CategoryDialog
import com.example.tasks.ui.dialogs.DateTimeDialog
import com.example.tasks.ui.dialogs.PriorityDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.time.OffsetDateTime

class AddFragment (val defaultDate: OffsetDateTime) : BottomSheetDialogFragment() {

    companion object {
        const val TAG = "AddTaskBottomSheet"
    }

    private val sharedViewModel: SharedViewModel by viewModels()
    private var newTask = Task(dateTime = defaultDate)
    private val timeDialog: DateTimeDialog by lazy { DateTimeDialog(requireContext()) }

    private var _binding: FragmentAddBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)

        timeDialog.dateTimeDialogListener = object : DateTimeDialog.DateTimeDialogListener {
            override fun onDateTimeSave(dateTime: OffsetDateTime) {
                newTask.dateTime = dateTime
            }
        }

        binding.timeIconAdd.setOnClickListener {chooseDateTime()}
        binding.categoryIconAdd.setOnClickListener { chooseCategory() }
        binding.priorityIconAdd.setOnClickListener { choosePriority() }
        binding.saveTaskIcon.setOnClickListener { saveTask() }

        return binding.root
    }

    private fun chooseDateTime() {
        timeDialog.showDateTimeDialog(newTask.dateTime)
        binding.timeIconAdd.changeColor(true)
    }

    private fun chooseCategory() {
        val myDialogFragment = CategoryDialog(newTask.category)
        myDialogFragment.categoryDialogListener =
            object : CategoryDialog.CategoryDialogListener {
                override fun onCategorySave(category: Category) {
                    newTask.category = category
                    binding.categoryIconAdd.changeColor(category != Category.NONE)
                }
            }
        myDialogFragment.show(parentFragmentManager, CategoryDialog.TAG)
    }

    private fun choosePriority() {
        val myDialogFragment = PriorityDialog(newTask.priority)
        myDialogFragment.priorityDialogListener =
            object : PriorityDialog.PriorityDialogListener {
                override fun onPrioritySave(priority: Priority) {
                    newTask.priority = priority
                    binding.priorityIconAdd.changeColor(priority != Priority.NONE)
                }
            }
        myDialogFragment.show(parentFragmentManager, PriorityDialog.TAG)
    }

    private fun saveTask() {
        val viewModel: TaskViewModel by viewModels()
        newTask.title = binding.titleEtAdd.getString()
        newTask.description = binding.descriptionEtAdd.getString()

        if (newTask.title.isNotEmpty()) {
            viewModel.insertTask(newTask)
            sharedViewModel.showSuccessToast(newTask.title, SharedViewModel.SUCCESS_ADD_TASK)
            dismiss()
        } else {
            sharedViewModel.showErrorToast(SharedViewModel.ERROR_ADD_TITLE)
        }
    }

    private fun EditText.getString() = text.toString().trim()
    private fun ImageView.changeColor(active: Boolean) {
        val iconColor = if (active) R.color.accent_color else R.color.white
        imageTintList = ColorStateList.valueOf(requireContext().getColor(iconColor))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
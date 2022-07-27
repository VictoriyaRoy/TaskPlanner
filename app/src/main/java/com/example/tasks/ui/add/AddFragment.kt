package com.example.tasks.ui.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.tasks.data.model.Task
import com.example.tasks.data.viewmodel.DataViewModel
import com.example.tasks.databinding.FragmentAddBinding
import com.example.tasks.ui.SharedViewModel
import com.example.tasks.ui.TaskViewModel
import com.example.tasks.ui.dialogs.DialogManager
import com.example.tasks.utils.DateTimeUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.time.OffsetDateTime

class AddFragment(private val defaultDate: OffsetDateTime = DateTimeUtil.todayEnd) :
    BottomSheetDialogFragment(), AddEventHandler {

    companion object {
        const val TAG = "AddTaskBottomSheet"
    }

    private val sharedVM: SharedViewModel by viewModels()
    private val taskVM: TaskViewModel by viewModels()
    private val dialogManager: DialogManager by lazy { DialogManager(taskVM) }

    private var _binding: FragmentAddBinding? = null
    private val binding
        get() = _binding!!

    interface AddTaskListener {
        fun onTaskAdd(task: Task)
    }

    var addTaskListener: AddTaskListener? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        binding.handler = this
        binding.viewmodel = taskVM
        binding.lifecycleOwner = this
        taskVM.dateTime.value = defaultDate

        return binding.root
    }

    override fun chooseDateTime() {
        dialogManager.chooseDateTime(requireContext())
    }

    override fun chooseCategory() {
        dialogManager.chooseCategory(parentFragmentManager)
    }

    override fun choosePriority() {
        dialogManager.choosePriority(parentFragmentManager)
    }

    override fun saveTask() {
        val dataVM: DataViewModel by viewModels()
        val title = sharedVM.fromEditText(binding.titleEtAdd)
        val description = sharedVM.fromEditText(binding.descriptionEtAdd)

        if (title.isNotEmpty()) {
            taskVM.title.value = title
            taskVM.description.value = description
            val newTask = taskVM.buildTask()

            dataVM.insertTask(newTask)
            sharedVM.showSuccessToast(newTask.title, SharedViewModel.SUCCESS_ADD_TASK)
            addTaskListener?.onTaskAdd(newTask)
            dismiss()
        } else {
            sharedVM.showErrorToast(SharedViewModel.ERROR_ADD_TITLE)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
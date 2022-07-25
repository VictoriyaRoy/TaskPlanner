package com.example.tasks.ui.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.tasks.data.viewmodel.DatabaseViewModel
import com.example.tasks.databinding.FragmentEditBinding
import com.example.tasks.ui.SharedViewModel
import com.example.tasks.ui.TaskViewModel
import com.example.tasks.ui.dialogs.DeleteDialog
import com.example.tasks.ui.dialogs.DialogManager


class EditFragment : Fragment(), EditEventHandler {
    private val databaseVM: DatabaseViewModel by viewModels()
    private val taskVM: TaskViewModel by viewModels()
    private val sharedVM: SharedViewModel by viewModels()

    private val dialogManager: DialogManager by lazy { DialogManager(taskVM) }
    private val args by navArgs<EditFragmentArgs>()

    private var _binding: FragmentEditBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        taskVM.fromTask(args.task)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentEditBinding.inflate(inflater, container, false)
        binding.handler = this
        binding.viewmodel = taskVM
        binding.lifecycleOwner = this

        binding.doneCheckEdit.setOnCheckedChangeListener { _, isDone ->
            taskVM.isDone.value = isDone
        }

        return binding.root
    }

    override fun saveChanges() {
        val task = taskVM.buildTask()
        databaseVM.updateTask(task)
        sharedVM.showSuccessToast(task.title, SharedViewModel.SUCCESS_UPDATE_TASK)
        navigateToList()
    }

    override fun deleteTask() {
        val task = taskVM.buildTask()
        val myDialogFragment = DeleteDialog(task.title)
        myDialogFragment.deleteDialogListener =
            object : DeleteDialog.DeleteDialogListener {
                override fun onTaskDelete() {
                    databaseVM.deleteTask(task)
                    sharedVM.showSuccessToast(
                        taskVM.title.value,
                        SharedViewModel.SUCCESS_DELETE_TASK
                    )
                    navigateToList()
                }
            }
        myDialogFragment.show(parentFragmentManager, DeleteDialog.TAG)
    }

    override fun editDateTime() {
        dialogManager.chooseDateTime(requireContext())
    }

    override fun editTitle() {
        dialogManager.editTitle(parentFragmentManager)
    }

    override fun editCategory() {
        dialogManager.chooseCategory(parentFragmentManager)
    }

    override fun editPriority() {
        dialogManager.choosePriority(parentFragmentManager)
    }

    private fun navigateToList() {
        val action = EditFragmentDirections.actionEditFragmentToListFragment(taskVM.dateTime.value)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package com.example.tasks.ui.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.tasks.R
import com.example.tasks.data.model.Category
import com.example.tasks.data.model.Task
import com.example.tasks.data.viewmodel.TaskViewModel
import com.example.tasks.databinding.FragmentEditBinding
import com.example.tasks.ui.dialogs.CategoryDialog
import com.example.tasks.ui.dialogs.TimeDialog
import java.time.OffsetDateTime


class EditFragment : Fragment() {

    private val viewModel: TaskViewModel by viewModels()
    private val args by navArgs<EditFragmentArgs>()
    private val currentTask: Task by lazy { args.task }
    private val timeDialog: TimeDialog by lazy { TimeDialog(requireContext()) }

    private var _binding: FragmentEditBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditBinding.inflate(inflater, container, false)
        updateTask()

        timeDialog.timeDialogListener = object : TimeDialog.TimeDialogListener {
            override fun onTimeSave(time: OffsetDateTime) {
                currentTask.time = time
                updateTask()
            }
        }

        binding.deleteTaskLayout.setOnClickListener {
            viewModel.deleteTask(currentTask)
            Toast.makeText(
                requireContext(),
                "Task '${currentTask.title}' successfully deleted",
                Toast.LENGTH_SHORT
            ).show()
            findNavController().navigate(R.id.action_editFragment_to_listFragment)
        }

        binding.doneCheckEdit.setOnCheckedChangeListener { _, isDone ->
            currentTask.isDone = isDone
        }

        binding.timeTvEdit.setOnClickListener {
            timeDialog.showDialogToEdit(currentTask)
        }

        binding.categoryLabelEdit.setOnClickListener {
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

        binding.saveChangesBtn.setOnClickListener {
            viewModel.updateTask(currentTask)
            findNavController().navigate(R.id.action_editFragment_to_listFragment)
        }

        return binding.root
    }

    private fun updateTask() {
        binding.task = currentTask
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
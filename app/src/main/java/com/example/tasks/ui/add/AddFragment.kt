package com.example.tasks.ui.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.tasks.data.model.Category
import com.example.tasks.data.model.Task
import com.example.tasks.data.viewmodel.TaskViewModel
import com.example.tasks.databinding.FragmentAddBinding
import com.example.tasks.ui.dialogs.CategoryDialog
import com.example.tasks.ui.dialogs.TimeDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.time.OffsetDateTime

class AddFragment : BottomSheetDialogFragment() {

    companion object {
        const val TAG = "AddTaskBottomSheet"
    }

    private var newTask = Task()
    private val timeDialog: TimeDialog by lazy { TimeDialog(requireContext()) }

    private var _binding: FragmentAddBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        val viewModel: TaskViewModel by viewModels()

        timeDialog.timeDialogListener = object : TimeDialog.TimeDialogListener {
            override fun onTimeSave(time: OffsetDateTime) {
                newTask.time = time
            }
        }

        binding.saveTaskIcon.setOnClickListener {
            newTask.title = binding.titleEtAdd.getString()
            newTask.description = binding.descriptionEtAdd.getString()
            if (newTask.title.isNotEmpty()) {
                viewModel.insertTask(newTask)
                binding.titleEtAdd.text.clear()
                binding.descriptionEtAdd.text.clear()
                Toast.makeText(requireContext(), "Task successfully added", Toast.LENGTH_SHORT)
                    .show()
                dismiss()
            } else {
                Toast.makeText(requireContext(), "Please add the title of task", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        binding.categoryIconAdd.setOnClickListener {
            val myDialogFragment = CategoryDialog(newTask.category)
            myDialogFragment.categoryDialogListener =
                object : CategoryDialog.CategoryDialogListener {
                    override fun onCategorySave(category: Category) {
                        newTask.category = category
                    }

                }
            myDialogFragment.show(parentFragmentManager, CategoryDialog.TAG)
        }

        binding.timeIconAdd.setOnClickListener {
            timeDialog.showDialogToAdd()
        }


        return binding.root
    }

    private fun EditText.getString() = text.toString().trim()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
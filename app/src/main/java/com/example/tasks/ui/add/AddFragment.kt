package com.example.tasks.ui.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.tasks.data.model.Task
import com.example.tasks.data.viewmodel.TaskViewModel
import com.example.tasks.databinding.FragmentAddBinding
import com.example.tasks.ui.category.CategoryDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddFragment : BottomSheetDialogFragment() {

    companion object {
        const val TAG = "AddTaskBottomSheet"
    }

    private var _binding: FragmentAddBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        val viewModel: TaskViewModel by viewModels()

        binding.saveTaskIcon.setOnClickListener {
            val title = binding.titleEtAdd.getString()
            val description = binding.descriptionEtAdd.getString()
            if (title.isNotEmpty()) {
                viewModel.insertTask(Task(0, title, description))
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
            val myDialogFragment = CategoryDialog()
            myDialogFragment.show(parentFragmentManager, CategoryDialog.TAG)
        }

        return binding.root
    }

    private fun EditText.getString() = text.toString().trim()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
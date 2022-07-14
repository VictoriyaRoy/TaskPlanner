package com.example.tasks.ui.edit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.tasks.R
import com.example.tasks.data.viewmodel.TaskViewModel
import com.example.tasks.databinding.FragmentEditBinding

class EditFragment : Fragment() {

    private val viewModel: TaskViewModel by viewModels()
    private val args by navArgs<EditFragmentArgs>()

    private var _binding: FragmentEditBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditBinding.inflate(inflater, container, false)
        binding.task = args.task

        binding.deleteTaskLayout.setOnClickListener {
            viewModel.deleteTask(args.task)
            Toast.makeText(
                requireContext(),
                "Task '${args.task.title}' successfully deleted",
                Toast.LENGTH_SHORT
            ).show()
            findNavController().navigate(R.id.action_editFragment_to_listFragment)
        }

        binding.doneCheckEdit.setOnCheckedChangeListener { _, isDone ->
            args.task.isDone = isDone
            viewModel.updateTask(args.task)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
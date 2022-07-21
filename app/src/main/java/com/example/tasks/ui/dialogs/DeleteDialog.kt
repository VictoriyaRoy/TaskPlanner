package com.example.tasks.ui.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.tasks.databinding.DeleteDialogBinding

class DeleteDialog(private val title: String) : DialogFragment() {
    companion object {
        const val TAG = "DeleteDialog"
    }

    interface DeleteDialogListener {
        fun onTaskDelete()
    }

    var deleteDialogListener: DeleteDialogListener? = null

    private var _binding: DeleteDialogBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val inflater = requireActivity().layoutInflater
        _binding = DeleteDialogBinding.inflate(inflater)
        builder.setView(binding.root)

        binding.title = title

        binding.cancelDeleteBtn.setOnClickListener { dismiss() }
        binding.acceptDeleteBtn.setOnClickListener {
            deleteDialogListener?.onTaskDelete()
            dismiss()
        }
        return builder.create()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
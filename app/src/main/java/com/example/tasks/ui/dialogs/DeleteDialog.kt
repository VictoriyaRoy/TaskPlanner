package com.example.tasks.ui.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.tasks.databinding.DeleteDialogBinding

class DeleteDialog(private val title: String) : DialogFragment(), DialogEventHandler {
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

        binding.handler = this
        binding.title = title
        return builder.create()
    }

    override fun negativeButton() {
        dismiss()
    }

    override fun positiveButton() {
        deleteDialogListener?.onTaskDelete()
        dismiss()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
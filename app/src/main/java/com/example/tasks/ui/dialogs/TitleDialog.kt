package com.example.tasks.ui.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.tasks.databinding.TitleDialogBinding
import com.example.tasks.ui.SharedViewModel


class TitleDialog(private val title: String, private val description: String) :
    DialogFragment(), DialogEventHandler {

    companion object {
        const val TAG = "TitleDialog"
    }

    interface TitleDialogListener {
        fun onTitleSave(title: String, description: String)
    }

    private val sharedViewModel: SharedViewModel by viewModels()
    var titleDialogListener: TitleDialogListener? = null

    private var _binding: TitleDialogBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val inflater = requireActivity().layoutInflater
        _binding = TitleDialogBinding.inflate(inflater)
        builder.setView(binding.root)

        binding.handler = this
        binding.titleEtEdit.setText(title)
        binding.descriptionEtEdit.setText(description)

        return builder.create()
    }

    override fun negativeButton() {
        dismiss()
    }

    override fun positiveButton() {
        val newTitle = binding.titleEtEdit.getString()
        val newDescription = binding.descriptionEtEdit.getString()

        if (newTitle.isNotEmpty()) {
            titleDialogListener?.onTitleSave(newTitle, newDescription)
            dismiss()
        } else {
            sharedViewModel.showErrorToast(SharedViewModel.ERROR_ADD_TITLE)
        }
    }

    private fun EditText.getString() = text.toString().trim()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

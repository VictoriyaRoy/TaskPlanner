package com.example.tasks.ui.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tasks.data.model.Category
import com.example.tasks.databinding.CategoryDialogBinding
import com.example.tasks.utils.adapters.CategoryAdapter


class CategoryDialog(private val initCategory: Category) : DialogFragment(), DialogEventHandler {
    companion object {
        const val TAG = "CategoryDialog"
    }

    interface CategoryDialogListener {
        fun onCategorySave(category: Category)
    }

    val adapter = CategoryAdapter(Category.values(), initCategory)
    var categoryDialogListener: CategoryDialogListener? = null

    private var _binding: CategoryDialogBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val inflater = requireActivity().layoutInflater
        _binding = CategoryDialogBinding.inflate(inflater)
        builder.setView(binding.root)

        binding.handler = this
        binding.categoryRecycler.layoutManager = GridLayoutManager(context, 3)
        binding.categoryRecycler.adapter = adapter

        return builder.create()
    }

    override fun negativeButton() {
        dismiss()
    }

    override fun positiveButton() {
        categoryDialogListener?.onCategorySave(adapter.chosenCategory)
        dismiss()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
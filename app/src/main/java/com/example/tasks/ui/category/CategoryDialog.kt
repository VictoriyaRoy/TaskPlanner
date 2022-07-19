package com.example.tasks.ui.category

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tasks.data.model.Category
import com.example.tasks.databinding.CategoryDialogBinding
import com.example.tasks.utils.adapters.CategoryAdapter


class CategoryDialog(private val initCategory: Category) : DialogFragment() {
    companion object {
        const val TAG = "CategoryDialog"
    }

    interface CategoryDialogListener {
        fun onCategorySave(category: Category)
    }

    var categoryDialogListener: CategoryDialogListener? = null

    private var _binding: CategoryDialogBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val inflater = requireActivity().layoutInflater
        _binding = CategoryDialogBinding.inflate(inflater)
        builder.setView(binding.root)

        val adapter = CategoryAdapter(Category.values(), initCategory)

        binding.categoryRecycler.layoutManager = GridLayoutManager(context, 3)
        binding.categoryRecycler.adapter = adapter

        binding.cancelCategoryBtn.setOnClickListener {
            dismiss()
        }

        binding.saveCategoryBtn.setOnClickListener {
            categoryDialogListener?.onCategorySave(adapter.chosenCategory)
            dismiss()
        }

        return builder.create()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package com.example.tasks.utils.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.tasks.data.model.Category
import com.example.tasks.databinding.CategoryItemBinding
import com.example.tasks.databinding.ChosenCategoryItemBinding

class CategoryAdapter(private val categoryList: Array<Category>, var chosenCategory: Category) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    companion object {
        const val TYPE_CHOSEN = 0
        const val TYPE_NOT_CHOSEN = 1
    }

    var previousPosition = categoryList.indexOf(chosenCategory)

    inner class CategoryViewHolder(private val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(category: Category) {
            if (binding is CategoryItemBinding) binding.category = category
            else if (binding is ChosenCategoryItemBinding) binding.category = category

            binding.root.setOnClickListener {
                chosenCategory = category
                notifyItemChanged(previousPosition)
                notifyItemChanged(adapterPosition)
                previousPosition = adapterPosition
            }
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = when (viewType) {
            TYPE_CHOSEN -> ChosenCategoryItemBinding.inflate(layoutInflater, parent, false)
            else -> CategoryItemBinding.inflate(layoutInflater, parent, false)
        }
        return CategoryViewHolder(binding)
    }

    override fun getItemViewType(position: Int) =
        if (categoryList[position] == chosenCategory) TYPE_CHOSEN else TYPE_NOT_CHOSEN


    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categoryList[position])
    }

    override fun getItemCount() = categoryList.size
}
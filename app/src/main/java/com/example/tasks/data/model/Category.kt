package com.example.tasks.data.model

import com.example.tasks.R
import java.util.*

enum class Category (val color: Int) {
    STUDY(R.color.study_category),
    WORK(R.color.work_category),
    HOME(R.color.home_category);

    override fun toString() = name.lowercase().replaceFirstChar { it.uppercase() }
}
package com.example.tasks.data.model

import com.example.tasks.R

enum class Priority(val value: Int, val color: Int) {
    HIGH(1, R.color.high_priority),
    MEDIUM(2, R.color.medium_priority),
    LOW(3, R.color.low_priority),
    NONE(4, R.color.white);

    override fun toString() = name.lowercase().replaceFirstChar { it.uppercase() }
}
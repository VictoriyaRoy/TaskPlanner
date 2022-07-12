package com.example.tasks.data.model

import com.example.tasks.R

enum class Priority (val color: Int) {
    HIGH(R.color.high_priority),
    MEDIUM(R.color.medium_priority),
    LOW(R.color.low_priority);

    override fun toString() = name.lowercase().replaceFirstChar { it.uppercase() }
}
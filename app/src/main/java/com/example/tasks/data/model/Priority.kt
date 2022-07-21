package com.example.tasks.data.model

import com.example.tasks.R

enum class Priority(val value: Int, val color: Int) {
    NONE(0, R.color.accent_color),
    LOW(1, R.color.low_priority),
    MID(2, R.color.medium_priority),
    HIGH(3, R.color.high_priority);

    override fun toString() = name.lowercase().replaceFirstChar { it.uppercase() }

    companion object{
        fun fromValue(value: Int) = values()[value]
    }
}
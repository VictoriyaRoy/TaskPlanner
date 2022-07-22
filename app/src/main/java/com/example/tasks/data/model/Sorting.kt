package com.example.tasks.data.model

enum class Sorting {
    BY_TIME,
    BY_PRIORITY;

    companion object {
        val defaultName = BY_TIME.name
    }
}
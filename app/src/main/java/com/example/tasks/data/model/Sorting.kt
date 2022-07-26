package com.example.tasks.data.model

import android.content.SharedPreferences

enum class Sorting {
    BY_TIME,
    BY_PRIORITY;

    companion object {
        private const val SORTING_TYPE = "sorting_type"
        val defaultSort = BY_TIME

        fun fromSharedPref(sharedPref: SharedPreferences): Sorting =
            sharedPref.getString(SORTING_TYPE, defaultSort.name)?.let { valueOf(it) } ?: defaultSort
    }

    fun writeToSharedPref(sharedPref: SharedPreferences) {
        with(sharedPref.edit()) {
            putString(SORTING_TYPE, name)
            apply()
        }
    }
}
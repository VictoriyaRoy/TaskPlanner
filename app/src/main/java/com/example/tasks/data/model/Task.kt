package com.example.tasks.data.model

import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class Task(
    val id: Int,
    var title: String,
    var time: OffsetDateTime,
    var category: Category,
    var priority: Priority,
    var isDone: Boolean
) {
    private val dateFormatter = DateTimeFormatter.ofPattern("dd MMM", Locale.ENGLISH)
    private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm", Locale.ENGLISH)

    val timeString: String
        get() {
            val today = OffsetDateTime.now().toLocalDate()
            val day = when (time.toLocalDate()) {
                today -> "Today"
                today.minusDays(1) -> "Yesterday"
                today.plusDays(1) -> "Tomorrow"
                else -> time.format(dateFormatter)
            }
            return "$day at ${time.format(timeFormatter)}"
        }
}
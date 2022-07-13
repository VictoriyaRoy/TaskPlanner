package com.example.tasks.data.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.tasks.R
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@Entity(tableName = "tasks")
class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var title: String,
    var time: OffsetDateTime,
    var category: Category? = null,
    var priority: Priority? = null,
    var isDone: Boolean = false
) {
    @Ignore
    private val dateFormatter = DateTimeFormatter.ofPattern("dd MMM", Locale.ENGLISH)

    @Ignore
    private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm", Locale.ENGLISH)

    @Ignore
    val categoryColor = category?.color ?: R.color.white

    @Ignore
    val priorityColor = priority?.color ?: R.color.white

    @Ignore
    fun timeToString(): String {
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
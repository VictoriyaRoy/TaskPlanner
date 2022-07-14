package com.example.tasks.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@Entity(tableName = "tasks")
@Parcelize
class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var title: String,
    var description: String = "",
    var time: OffsetDateTime = OffsetDateTime.now(),
    var category: Category = Category.NONE,
    var priority: Priority = Priority.NONE,
    var isDone: Boolean = false
) : Parcelable {

    companion object {
        @Ignore
        private val dateFormatter = DateTimeFormatter.ofPattern("dd MMM", Locale.ENGLISH)

        @Ignore
        private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm", Locale.ENGLISH)
    }

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
package com.example.tasks.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.tasks.utils.DateTimeUtil
import kotlinx.parcelize.Parcelize
import java.time.OffsetDateTime

@Entity(tableName = "tasks")
@Parcelize
class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var title: String = "",
    var description: String = "",
    var dateTime: OffsetDateTime = OffsetDateTime.now(),
    var category: Category = Category.NONE,
    var priority: Priority = Priority.NONE,
    var isDone: Boolean = false
) : Parcelable
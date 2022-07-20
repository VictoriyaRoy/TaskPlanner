package com.example.tasks.data

import androidx.room.TypeConverter
import com.example.tasks.data.model.Category
import com.example.tasks.data.model.Priority
import com.example.tasks.utils.DateTimeUtils
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

object Converter {

    @TypeConverter
    @JvmStatic
    fun toOffsetDateTime(value: String?): OffsetDateTime? {
        return value?.let {
            DateTimeUtils.timestampToDate(it)
        }
    }

    @TypeConverter
    @JvmStatic
    fun fromOffsetDateTime(date: OffsetDateTime?): String? {
        return date?.let {
            DateTimeUtils.dateToTimestamp(it)
        }
    }

    @TypeConverter
    fun fromPriority(priority: Priority) = priority.value

    @TypeConverter
    fun toPriority(value: Int) = Priority.values()[value - 1]

    @TypeConverter
    fun fromCategory(category: Category) = category.name

    @TypeConverter
    fun toCategory(name: String) = Category.valueOf(name)
}
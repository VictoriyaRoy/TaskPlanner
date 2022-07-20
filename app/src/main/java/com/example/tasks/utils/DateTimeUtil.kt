package com.example.tasks.utils

import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*

class DateTimeUtil {
    companion object {
        private val dateFormatter = DateTimeFormatter.ofPattern("d MMMM", Locale.ENGLISH)
        private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm", Locale.ENGLISH)
        private val timestampFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

        fun startOfDay(dateTime: OffsetDateTime): OffsetDateTime =
            OffsetDateTime.of(
                dateTime.toLocalDate(),
                LocalTime.MIDNIGHT,
                ZoneOffset.UTC
            )

        fun dateTimeAsString(dateTime: OffsetDateTime) =
            "${dateAsString(dateTime)} at ${dateTime.format(timeFormatter)}"

        fun dateAsString(dateTime: OffsetDateTime): String {
            val today = OffsetDateTime.now().toLocalDate()

            return when (dateTime.toLocalDate()) {
                today -> "Today"
                today.minusDays(1) -> "Yesterday"
                today.plusDays(1) -> "Tomorrow"
                else -> dateTime.format(dateFormatter)
            }
        }

        fun dateToTimestamp(date: OffsetDateTime): String = date.format(timestampFormatter)
        fun timestampToDate(timeStamp: String): OffsetDateTime =
            timestampFormatter.parse(timeStamp, OffsetDateTime::from)
    }
}
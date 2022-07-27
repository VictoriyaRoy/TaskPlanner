package com.example.tasks.utils

import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*

class DateTimeUtil {
    companion object {
        const val SHORT_FORMAT = 0
        const val FULL_FORMAT = 1

        private val shortDateFormatter = DateTimeFormatter.ofPattern("d MMM", Locale.ENGLISH)
        private val fullDateFormatter = DateTimeFormatter.ofPattern("EEE, d MMM", Locale.ENGLISH)
        private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm", Locale.ENGLISH)
        private val timestampFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

        val todayStart: OffsetDateTime
            get() = startOfDay(OffsetDateTime.now())
        val todayEnd: OffsetDateTime
            get() = endOfDay(todayStart)

        fun startOfDay(dateTime: OffsetDateTime): OffsetDateTime =
            OffsetDateTime.of(
                dateTime.toLocalDate(),
                LocalTime.MIDNIGHT,
                ZoneOffset.UTC
            )

        fun endOfDay(start: OffsetDateTime): OffsetDateTime =
            start.plusDays(1).minusHours(1)

        fun dateTimeAsString(dateTime: OffsetDateTime) =
            "${dateAsString(dateTime, SHORT_FORMAT)} at ${dateTime.format(timeFormatter)}"

        fun dateAsString(dateTime: OffsetDateTime, format: Int): String {
            val today = OffsetDateTime.now().toLocalDate()
            val formatter = when (format) {
                SHORT_FORMAT -> shortDateFormatter
                FULL_FORMAT -> fullDateFormatter
                else -> return ""
            }

            return when (dateTime.toLocalDate()) {
                today -> "Today"
                today.minusDays(1) -> "Yesterday"
                today.plusDays(1) -> "Tomorrow"
                else -> dateTime.format(formatter)
            }
        }

        fun dateToTimestamp(date: OffsetDateTime): String = date.format(timestampFormatter)
        fun timestampToDate(timeStamp: String): OffsetDateTime =
            timestampFormatter.parse(timeStamp, OffsetDateTime::from)
    }
}
package com.example.tasks.ui.dialogs

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import com.example.tasks.R
import java.time.OffsetDateTime
import java.time.ZoneOffset

class DateTimeDialog(val context: Context) {
    interface DateTimeDialogListener {
        fun onDateTimeSave(dateTime: OffsetDateTime)
    }

    var dateTimeDialogListener: DateTimeDialogListener? = null

    fun showDateTimeDialog(initDateTime: OffsetDateTime = OffsetDateTime.now()) {
        chooseDateTime(initDateTime)
    }

    fun showOnlyDateDialog(initDateTime: OffsetDateTime = OffsetDateTime.now()) {
        chooseOnlyDate(initDateTime)
    }

    private fun chooseOnlyDate(initDateTime: OffsetDateTime) {
        val dateDialog = DatePickerDialog(
            context,
            R.style.DateTimeDialog,
            { _, year, month, day ->
                val result = OffsetDateTime.of(
                    year, month + 1, day, 0, 0, 0, 0, ZoneOffset.UTC
                )
                dateTimeDialogListener?.onDateTimeSave(result)
            },
            initDateTime.year,
            initDateTime.monthValue - 1,
            initDateTime.dayOfMonth
        )
        dateDialog.show()
    }

    private fun chooseDateTime(initDateTime: OffsetDateTime) {
        val dateDialog = DatePickerDialog(
            context,
            R.style.DateTimeDialog,
            { _, year, month, day ->
                chooseTime(initDateTime, year, month, day)
            },
            initDateTime.year,
            initDateTime.monthValue - 1,
            initDateTime.dayOfMonth
        )
        dateDialog.show()
    }

    private fun chooseTime(initDateTime: OffsetDateTime, year: Int, month: Int, day: Int) {
        val timeDialog = TimePickerDialog(
            context,
            R.style.DateTimeDialog,
            { _, hour, minute ->
                val result = OffsetDateTime.of(
                    year, month + 1, day, hour, minute, 0, 0, ZoneOffset.UTC
                )
                dateTimeDialogListener?.onDateTimeSave(result)
            },
            initDateTime.hour,
            initDateTime.minute,
            true
        )
        timeDialog.show()
    }
}
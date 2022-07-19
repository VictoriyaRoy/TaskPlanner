package com.example.tasks.ui.dialogs

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import com.example.tasks.R
import com.example.tasks.data.model.Task
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.util.*

class TimeDialog(val context: Context) {
    interface TimeDialogListener {
        fun onTimeSave(time: OffsetDateTime)
    }

    var timeDialogListener: TimeDialogListener? = null

    fun showDialogToEdit(task: Task) {
        chooseDate(task.time)
    }

    fun showDialogToAdd() {
        chooseDate(OffsetDateTime.now())
    }

    private fun chooseDate(default: OffsetDateTime) {
        val dateDialog = DatePickerDialog(
            context,
            R.style.DateTimeDialog,
            { _, year, month, day ->
                chooseTime(default, year, month, day)
            },
            default.year,
            default.monthValue - 1,
            default.dayOfMonth
        )
        dateDialog.show()
    }

    private fun chooseTime(default: OffsetDateTime, year: Int, month: Int, day: Int) {
        val timeDialog = TimePickerDialog(
            context,
            R.style.DateTimeDialog,
            { _, hour, minute ->
                val result = OffsetDateTime.of(
                    year, month + 1, day, hour, minute, 0, 0, ZoneOffset.UTC
                )
                timeDialogListener?.onTimeSave(result)
            },
            default.hour,
            default.minute,
            true
        )
        timeDialog.show()
    }
}
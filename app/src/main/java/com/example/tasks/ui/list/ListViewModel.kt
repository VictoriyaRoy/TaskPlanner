package com.example.tasks.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tasks.utils.DateTimeUtil
import java.time.OffsetDateTime

class ListViewModel : ViewModel() {
    private val _date = MutableLiveData(DateTimeUtil.todayStart)
    val date: LiveData<OffsetDateTime> = _date

    fun setDate(newDate: OffsetDateTime?) {
        _date.value = newDate
    }

    fun previousDate() {
        setDate(_date.value?.minusDays(1))
    }

    fun nextDate() {
        setDate(_date.value?.plusDays(1))
    }
}
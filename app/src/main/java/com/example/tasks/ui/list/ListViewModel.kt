package com.example.tasks.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.tasks.data.model.Sorting
import com.example.tasks.utils.DateTimeUtil
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.OffsetDateTime

class ListViewModel : ViewModel() {

    private val _params =
        MutableStateFlow(Triple<OffsetDateTime, Sorting, String?>
            (DateTimeUtil.todayStart, Sorting.defaultSort, null))
    val params = _params.asLiveData()

    fun setDate(newDate: OffsetDateTime) {
        val value = _params.value
        _params.value = Triple(newDate, value.second, value.third)
    }

    fun previousDate() {
        setDate(_params.value.first.minusDays(1))
    }

    fun nextDate() {
        setDate(_params.value.first.plusDays(1))
    }

    fun setSorting(sorting: Sorting) {
        val value = _params.value
        _params.value = Triple(value.first, sorting, value.third)
    }

    fun setSearch(query: String?) {
        val value = _params.value
        _params.value = Triple(value.first, value.second, query)
    }
}
package com.example.plentifood.ui.screens.filters

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import kotlin.collections.addAll
import kotlin.text.clear

class FilterViewModel: ViewModel() {

    val dayOptions = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
    val dayChecked =  mutableStateListOf(false, false, false, false, false, false, false)

    val orgOptions = listOf("Food Bank", "Church", "Nonprofit", "Community Center", "Others")
    val orgChecked = mutableStateListOf(false, false, false, false, false)

    val serviceOptions = listOf("Food Bank", "Meal")
    val serviceChecked = mutableStateListOf(false, false)

    var AreFiltersApplied = false

    fun resetUi() {
        for (index in dayChecked.indices) dayChecked[index] = false
        for (index in orgChecked.indices) orgChecked[index] = false
        for (index in serviceChecked.indices) serviceChecked[index] = false
    }

    fun getNumOfFilters(): Int {
        return dayChecked.count { it } + orgChecked.count { it } + serviceChecked.count { it }
    }

    fun updateFiltersStatus(bool: Boolean) {
        AreFiltersApplied = bool
    }

}
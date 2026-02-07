package com.example.plentifood.ui.state

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalTime


data class DayHoursValue (
    val open: LocalTime?,
    val close: LocalTime?
)

@RequiresApi(Build.VERSION_CODES.O)
fun defaultHours(): Map<String, DayHoursValue> = mapOf(
    "Monday" to DayHoursValue(null, null),
    "Tuesday" to DayHoursValue(null, null),
    "Wednesday" to DayHoursValue(null, null),
    "Thursday" to DayHoursValue(null, null),
    "Friday" to DayHoursValue(null, null),
    "Saturday" to DayHoursValue(null, null),
    "Sunday" to DayHoursValue(null, null),
)

@RequiresApi(Build.VERSION_CODES.O)
data class AddSiteUiState (
    val siteName: String = "",
    val address1: String = "",
    val address2: String = "",
    val city: String = "",
    val state: String = "",
    val postalCode: String = "",
    val phone: String = "",
    val eligibility: String = "Select Eligibility *",
    val notes: String = "",
    val serviceTypes: List<String> = emptyList(),
    val hoursByDay: Map<String, DayHoursValue> = defaultHours()
)
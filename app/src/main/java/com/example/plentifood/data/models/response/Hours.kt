package com.example.plentifood.data.models.response

data class Hours(
    val monday: List<HourSlot>,
    val tuesday: List<HourSlot>,
    val wednesday: List<HourSlot>,
    val thursday: List<HourSlot>,
    val friday: List<HourSlot>,
    val saturday: List<HourSlot>,
    val sunday: List<HourSlot>
)
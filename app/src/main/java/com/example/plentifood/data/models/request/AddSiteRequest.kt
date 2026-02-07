package com.example.plentifood.data.models.request

import com.example.plentifood.data.models.response.Hours
import com.google.gson.annotations.SerializedName;

data class AddSiteRequest (
    val name: String,
    @SerializedName("address_line1")
    val addressLine1: String,
    @SerializedName("address_line2")
    val addressLine2: String,
    val city: String,
    val state: String,
    @SerializedName("postal_code")
    val postalCode: String,
    val phone: String,
    val eligibility: String,
    @SerializedName("service_notes")
    val serviceNotes: String,
    val services: List<String>,
    val hours: Map<String, List<DayHoursRequest>>,
    )


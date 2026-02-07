package com.example.plentifood.data.models.response

import com.google.gson.annotations.SerializedName

data class Site(
    val id: Int,
    val name: String,

    @SerializedName("address_line1")
    val addressLine1: String,

    @SerializedName("address_line2")
    val addressLine2: String?,

    val city: String,
    val state: String,

    @SerializedName("postal_code")
    val postalCode: String,

    val status: String,
    val eligibility: String,
    val hours: Hours,
    val latitude: Double,
    val longitude: Double,
    val phone: String,

    @SerializedName("service_notes")
    val serviceNotes: String,

    val services: List<Service>,

    @SerializedName("created_at")
    val createdAt: String,

    @SerializedName("updated_at")
    val updatedAt: String?,

    @SerializedName( "organization_id")
    val organizationId: Int,

    @SerializedName( "organization_name")
    val organizationName: String,

    @SerializedName( "organization_type")
    val organizationType: String,

    @SerializedName( "organization_website_url")
    val organizationWebsiteUrl: String?






)
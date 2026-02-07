package com.example.plentifood.data.models.response

import com.google.gson.annotations.SerializedName

data class OrganizationResponse(
    val id: Int,
    val name: String,

    @SerializedName("organization_type")
    val organizationType: String,

    @SerializedName("website_url")
    val websiteUrl: String?,

    @SerializedName("created_at")
    val createdAt: String,

    @SerializedName("updated_at")
    val updatedAt: String?,

    @SerializedName("sites")
    val sites: List<Site>
)


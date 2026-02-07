package com.example.plentifood.data.models.request

import com.google.gson.annotations.SerializedName

data class OrgRequest(
    val name: String,
    @SerializedName("website_url")
    val websiteUrl: String,
    @SerializedName("organization_type")
    val organizationType: String
)
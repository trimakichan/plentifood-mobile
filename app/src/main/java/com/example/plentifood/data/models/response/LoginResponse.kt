package com.example.plentifood.data.models.response

import com.google.gson.annotations.SerializedName

data class LoginResponse (
    val id: Int,
    val username: String,
    @SerializedName("organization_id")
    val organizationId: Int,
)



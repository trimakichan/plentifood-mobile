package com.example.plentifood.data.models.response

import com.google.gson.annotations.SerializedName


data class RegisterResponse (

    @SerializedName("admin_user")
    val adminUser: AdminUserResponse,

    val organization: OrganizationResponse

)
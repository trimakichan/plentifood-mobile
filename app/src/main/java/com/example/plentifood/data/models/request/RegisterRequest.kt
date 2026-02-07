package com.example.plentifood.data.models.request

data class RegisterRequest(
    val organization: OrgRequest,
    val admin: AdminRequest
)
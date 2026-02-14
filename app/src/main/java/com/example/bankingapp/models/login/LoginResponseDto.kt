package com.example.bankingapp.models.login

import com.example.bankingapp.utils.Role
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginResponseDto (
    val username: String,
    val role: Role,
    val token: String
)
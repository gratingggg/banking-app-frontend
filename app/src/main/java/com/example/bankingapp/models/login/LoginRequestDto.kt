package com.example.bankingapp.models.login

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginRequestDto(
    val username: String,
    val password: String
)

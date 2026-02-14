package com.example.bankingapp.models.exception

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ErrorResponse(
    val message: String,
    val statusCode: Int
)

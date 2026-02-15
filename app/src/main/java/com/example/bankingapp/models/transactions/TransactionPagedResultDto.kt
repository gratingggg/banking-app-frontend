package com.example.bankingapp.models.transactions

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TransactionPagedResultDto(
    val content: List<TransactionResponseDto>,
    val number: Int,
    val size: Int,
    val totalPages: Int,
    val totalElements: Int
)
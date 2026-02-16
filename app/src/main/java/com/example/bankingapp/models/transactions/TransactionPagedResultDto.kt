package com.example.bankingapp.models.transactions

import com.example.bankingapp.utils.Page
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TransactionPagedResultDto(
    val content: List<TransactionResponseDto>,
    val page: Page
)
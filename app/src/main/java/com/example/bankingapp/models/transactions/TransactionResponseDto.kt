package com.example.bankingapp.models.transactions

import com.example.bankingapp.utils.TransactionStatus
import com.example.bankingapp.utils.TransactionType
import com.squareup.moshi.JsonClass
import java.math.BigDecimal
import java.time.LocalDateTime

@JsonClass(generateAdapter = true)
data class TransactionResponseDto(
    val dateOfTransaction: LocalDateTime,
    val amount: BigDecimal,
    val fromAccountId: Long? = null,
    val toAccountId: Long? = null,
    val loanId: Long? = null,
    val transactionId: Long,
    val transactionStatus: TransactionStatus,
    val transactionType: TransactionType,
    val failureReason: String? = null,
    val handledBy: String? = null
)

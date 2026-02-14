package com.example.bankingapp.models.transactions

import com.example.bankingapp.utils.TransactionType
import com.squareup.moshi.JsonClass
import java.math.BigDecimal

@JsonClass(generateAdapter = true)
data class TransactionRequestDto(
    val amount: BigDecimal,
    val fromAccountId: Long? = null,
    val toAccountId: Long? = null,
    val loanId: Long? = null,
    val transactionType: TransactionType
)
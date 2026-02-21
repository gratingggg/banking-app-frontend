package com.example.bankingapp.models.transactions

data class TransactionSummary(
    val transactionId: Long,
    val otherCustomer: String? = null,
    val dateOfTransaction: String,
    val amount: String,
    val credit: Boolean
)
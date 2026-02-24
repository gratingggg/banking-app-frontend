package com.example.bankingapp.utils

data class TransactionParams(
    val customerId: Long? = null,
    val accountId: Long? = null,
    val transactionStatus: TransactionStatus? = null,
    val transactionType: TransactionType? = null,
    val fromDate: String? = null,
    val toDate: String? = null
)
package com.example.bankingapp.utils

data class LoanParams(
    val customerId: String? = null,
    val loanStatus: LoanStatus? = null,
    val loanType: LoanType? = null,
    val fromDate: String? = null,
    val toDate: String? = null
)
package com.example.bankingapp.models.loan

import com.squareup.moshi.JsonClass
import java.math.BigDecimal

@JsonClass(generateAdapter = true)
data class LoanRepaymentDto(
    val loanId: Long,
    val repayAmount: BigDecimal
)

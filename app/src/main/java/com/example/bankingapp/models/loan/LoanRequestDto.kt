package com.example.bankingapp.models.loan

import com.example.bankingapp.utils.LoanType
import com.squareup.moshi.JsonClass
import java.math.BigDecimal

@JsonClass(generateAdapter = true)
data class LoanRequestDto(
    val accountId: Long,
    val tenureInMonths: Int,
    val loanType: LoanType,
    val principalAmount: BigDecimal
)

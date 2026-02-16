package com.example.bankingapp.models.loan

import com.example.bankingapp.utils.AccountStatus
import com.example.bankingapp.utils.LoanStatus
import com.example.bankingapp.utils.LoanType
import com.squareup.moshi.JsonClass
import java.math.BigDecimal
import java.time.LocalDate

@JsonClass(generateAdapter = true)
data class LoanResponseDto(
    val loanId: Long,
    val accountId: Long,
    val dateOfIssuance: LocalDate?,
    val tenureInMonths: Int,
    val dateOfRepayment: LocalDate?,
    val loanType: LoanType,
    val loanStatus: LoanStatus,
    val principalAmount: BigDecimal,
    val rateOfInterest: BigDecimal,
    val emi: BigDecimal,
    val outstandingAmount: BigDecimal?
)

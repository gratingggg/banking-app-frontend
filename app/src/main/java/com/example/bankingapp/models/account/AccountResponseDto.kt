package com.example.bankingapp.models.account

import com.example.bankingapp.utils.AccountStatus
import com.example.bankingapp.utils.AccountType
import com.squareup.moshi.JsonClass
import java.math.BigDecimal
import java.time.LocalDate

@JsonClass(generateAdapter = true)
data class AccountResponseDto(
    val accountId: Long,
    val accountType: AccountType,
    val accountStatus: AccountStatus,
    val balance: BigDecimal,
    val dateOfIssuance: LocalDate,
    val customerName: String
)

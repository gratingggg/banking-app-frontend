package com.example.bankingapp.models.account

import com.example.bankingapp.utils.AccountType
import com.squareup.moshi.JsonClass
import java.math.BigDecimal

@JsonClass(generateAdapter = true)
data class AccountBalanceResponseDto(
    val balance: BigDecimal,
    val accountId: Long,
    val accountType: AccountType
)

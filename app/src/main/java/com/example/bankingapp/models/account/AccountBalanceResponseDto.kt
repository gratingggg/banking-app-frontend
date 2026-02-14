package com.example.bankingapp.models.account

import com.squareup.moshi.JsonClass
import java.math.BigDecimal

@JsonClass(generateAdapter = true)
data class AccountBalanceResponseDto(
    val balance: BigDecimal
)

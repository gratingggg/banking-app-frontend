package com.example.bankingapp.models.account

import com.example.bankingapp.utils.AccountType
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AccountSummaryDto(
    val accountId: Long,
    val accountType: AccountType
)

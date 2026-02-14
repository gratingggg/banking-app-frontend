package com.example.bankingapp.models.account

import com.example.bankingapp.utils.AccountType
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)

data class AccountRequestDto(
    val accountType: AccountType
)

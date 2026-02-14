package com.example.bankingapp.models.customer

import com.example.bankingapp.utils.Gender
import com.squareup.moshi.JsonClass
import java.time.LocalDate

@JsonClass(generateAdapter = true)
data class CustomerResponseDto (
    val name: String,
    val email: String,
    val phoneNumber: String,
    val dateOfBirth: LocalDate,
    val address: String,
    val gender: Gender,
    val username: String
)
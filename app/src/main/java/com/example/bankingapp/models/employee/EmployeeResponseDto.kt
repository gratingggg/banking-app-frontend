package com.example.bankingapp.models.employee

import com.example.bankingapp.utils.EmployeeRole
import com.example.bankingapp.utils.EmployeeStatus
import com.example.bankingapp.utils.Gender
import com.squareup.moshi.JsonClass
import java.time.LocalDate

@JsonClass(generateAdapter = true)
data class EmployeeResponseDto(
    val name: String,
    val email: String,
    val phoneNumber: String,
    val dateOfBirth: LocalDate,
    val address: String,
    val gender: Gender,
    val username: String,
    val employeeRole: EmployeeRole,
    val employeeStatus: EmployeeStatus
)

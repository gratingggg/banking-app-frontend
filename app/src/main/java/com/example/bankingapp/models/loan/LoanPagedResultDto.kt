package com.example.bankingapp.models.loan

data class LoanPagedResultDto (
    val content: List<LoanResponseDto>,
    val number: Int,
    val size: Int,
    val totalPages: Int,
    val totalElements: Int
)
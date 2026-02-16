package com.example.bankingapp.models.loan

import com.example.bankingapp.utils.Page

data class LoanPagedResultDto (
    val content: List<LoanResponseDto>,
    val page: Page
)
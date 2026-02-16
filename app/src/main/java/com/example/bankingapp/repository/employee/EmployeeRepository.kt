package com.example.bankingapp.repository.employee

import com.example.bankingapp.models.employee.EmployeeResponseDto
import com.example.bankingapp.models.loan.LoanPagedResultDto
import com.example.bankingapp.models.loan.LoanResponseDto
import com.example.bankingapp.models.transactions.TransactionResponseDto
import com.example.bankingapp.utils.ApiResult
import com.example.bankingapp.utils.LoanType

interface EmployeeRepository {
    suspend fun getMyDetails(): ApiResult<EmployeeResponseDto>

    suspend fun processLoan(loanId: Long, action: String): ApiResult<LoanResponseDto>

    suspend fun disburseLoan(loanId: Long): ApiResult<TransactionResponseDto>
}
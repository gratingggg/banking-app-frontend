package com.example.bankingapp.repository.loan

import com.example.bankingapp.models.loan.LoanPagedResultDto
import com.example.bankingapp.models.loan.LoanRequestDto
import com.example.bankingapp.models.loan.LoanResponseDto
import com.example.bankingapp.models.transactions.TransactionPagedResultDto
import com.example.bankingapp.utils.ApiResult
import com.example.bankingapp.utils.LoanStatus
import com.example.bankingapp.utils.LoanType
import java.time.LocalDate

interface EmployeeLoanRepository {
    suspend fun createLoanByEmployee(
        accountId: Long, loanRequestDto: LoanRequestDto
    ): ApiResult<LoanResponseDto>

    suspend fun getAllLoansByEmployee(
        customerId: Long,
        page: Int? = null,
        size: Int? = null,
        loanStatus: LoanStatus? = null,
        loanType: LoanType? = null,
        fromDate: LocalDate? = null,
        toDate: LocalDate? = null
    ): ApiResult<LoanPagedResultDto>

    suspend fun getParticularLoanByEmployee(loanId: Long): ApiResult<LoanResponseDto>

    suspend fun getLoanTransactionsByEmployee(
        loanId: Long,
        page: Int? = null,
        size: Int? = null,
        loanStatus: LoanStatus? = null,
        loanType: LoanType? = null,
        fromDate: LocalDate? = null,
        toDate: LocalDate? = null
    ): ApiResult<TransactionPagedResultDto>
}
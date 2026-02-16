package com.example.bankingapp.repository.loan

import android.util.Log
import com.example.bankingapp.models.exception.ErrorResponse
import com.example.bankingapp.models.loan.LoanPagedResultDto
import com.example.bankingapp.models.loan.LoanRepaymentDto
import com.example.bankingapp.models.loan.LoanRequestDto
import com.example.bankingapp.models.loan.LoanResponseDto
import com.example.bankingapp.models.transactions.TransactionPagedResultDto
import com.example.bankingapp.models.transactions.TransactionResponseDto
import com.example.bankingapp.network.LoanApiService
import com.example.bankingapp.network.RetrofitInstance
import com.example.bankingapp.utils.ApiResult
import com.example.bankingapp.utils.LoanStatus
import com.example.bankingapp.utils.LoanType
import retrofit2.Response
import java.time.LocalDate

class LoanRepositoryImpl (
    private val loanApiService: LoanApiService
): CustomerLoanRepository, EmployeeLoanRepository{

    private fun <T> helper(result: Response<T>): ApiResult<T> {
        return try {
            if (result.isSuccessful) {
                ApiResult.Success(result.body()!!)
            } else {
                val error = RetrofitInstance.parseError(result.errorBody())
                ApiResult.Failure(error!!)
            }
        } catch (e: Exception) {
            ApiResult.Failure(
                ErrorResponse(
                    message = e.message.toString(),
                    statusCode = 6969
                )
            )
        }
    }

    override suspend fun createLoan(loanRequestDto: LoanRequestDto): ApiResult<LoanResponseDto> {
        val result = loanApiService.createLoanByCustomer(loanRequestDto)
        return helper(result)
    }

    override suspend fun repayLoan(loanRepaymentDto: LoanRepaymentDto): ApiResult<TransactionResponseDto> {
        val result = loanApiService.repayLoanByCustomer(loanRepaymentDto)
        return helper(result)
    }

    override suspend fun getAllLoansByCustomer(
        page: Int?,
        size: Int?,
        loanStatus: LoanStatus?,
        loanType: LoanType?,
        fromDate: LocalDate?,
        toDate: LocalDate?
    ): ApiResult<LoanPagedResultDto> {
        val result = loanApiService.getAllLoansByCustomer(
            page = page,
            size = size,
            loanStatus = loanStatus,
            loanType = loanType,
            fromDate = fromDate,
            toDate = toDate
        )
        return helper(result)
    }

    override suspend fun getParticularLoan(loanId: Long): ApiResult<LoanResponseDto> {
        val result = loanApiService.getParticularLoanByCustomer(loanId)
        return helper(result)
    }

    override suspend fun getAllTransactions(
        loanId: Long,
        page: Int?,
        size: Int?,
        loanStatus: LoanStatus?,
        loanType: LoanType?,
        fromDate: LocalDate?,
        toDate: LocalDate?
    ): ApiResult<TransactionPagedResultDto> {
        val result = loanApiService.getLoanTransactionsByCustomer(
            loanId = loanId,
            page = page,
            size = size,
            loanStatus = loanStatus,
            loanType = loanType,
            fromDate = fromDate,
            toDate = toDate
        )
        return helper(result)
    }

    override suspend fun createLoanByEmployee(
        accountId: Long,
        loanRequestDto: LoanRequestDto
    ): ApiResult<LoanResponseDto> {
        val result = loanApiService.createLoanByEmployee(accountId, loanRequestDto)
        return helper(result)
    }

    override suspend fun getAllCustomerLoansByEmployee(
        customerId: Long,
        page: Int?,
        size: Int?,
        loanStatus: LoanStatus?,
        loanType: LoanType?,
        fromDate: LocalDate?,
        toDate: LocalDate?
    ): ApiResult<LoanPagedResultDto> {
        val result = loanApiService.getAllLoansByEmployee(
            customerId = customerId,
            page = page,
            size = size,
            loanStatus = loanStatus,
            loanType = loanType,
            fromDate = fromDate,
            toDate = toDate
        )
        return helper(result)
    }

    override suspend fun getParticularLoanByEmployee(loanId: Long): ApiResult<LoanResponseDto> {
        val result = loanApiService.getParticularLoanBYEmployee(loanId)
        return helper(result)
    }

    override suspend fun getLoanTransactionsByEmployee(
        loanId: Long,
        page: Int?,
        size: Int?,
        loanStatus: LoanStatus?,
        loanType: LoanType?,
        fromDate: LocalDate?,
        toDate: LocalDate?
    ): ApiResult<TransactionPagedResultDto> {
        val result = loanApiService.getLoanTransactionsByEmployee(
            loanId = loanId,
            page = page,
            size = size,
            loanStatus = loanStatus,
            loanType = loanType,
            fromDate = fromDate,
            toDate = toDate
        )
        return helper(result)
    }

    override suspend fun getAllLoansByEmployee(
        page: Int?,
        size: Int?,
        loanStatus: LoanStatus?,
        loanType: LoanType?,
        fromDate: LocalDate?,
        toDate: LocalDate?
    ): ApiResult<LoanPagedResultDto> {
        val result = loanApiService.getAllLoans(
            page = page,
            size = size,
            loanStatus = loanStatus,
            loanType = loanType,
            fromDate = fromDate,
            toDate = toDate
        )
        return helper(result)
    }
}


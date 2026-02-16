package com.example.bankingapp.repository.employee

import android.R.attr.action
import android.util.Log
import com.example.bankingapp.models.employee.EmployeeResponseDto
import com.example.bankingapp.models.exception.ErrorResponse
import com.example.bankingapp.models.loan.LoanPagedResultDto
import com.example.bankingapp.models.loan.LoanResponseDto
import com.example.bankingapp.models.transactions.TransactionResponseDto
import com.example.bankingapp.network.EmployeeApiService
import com.example.bankingapp.network.RetrofitInstance
import com.example.bankingapp.utils.ApiResult
import com.example.bankingapp.utils.LoanType
import retrofit2.Response

class EmployeeRepositoryImpl(
    private val employeeApiService: EmployeeApiService
): EmployeeRepository {
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

    override suspend fun getMyDetails(): ApiResult<EmployeeResponseDto> {
        val result = employeeApiService.getMyDetails()
        return helper(result)
    }

    override suspend fun processLoan(
        loanId: Long,
        action: String
    ): ApiResult<LoanResponseDto> {
        val result = employeeApiService.processLoans(loanId, action)
        return helper(result)
    }

    override suspend fun disburseLoan(
        loanId: Long
    ): ApiResult<TransactionResponseDto> {
        val result = employeeApiService.disburseLoans(loanId)
        return helper(result)
    }
}
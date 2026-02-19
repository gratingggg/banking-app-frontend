package com.example.bankingapp.repository.transaction

import android.util.Log
import com.example.bankingapp.models.exception.ErrorResponse
import com.example.bankingapp.models.transactions.TransactionPagedResultDto
import com.example.bankingapp.models.transactions.TransactionRequestDto
import com.example.bankingapp.models.transactions.TransactionResponseDto
import com.example.bankingapp.network.RetrofitInstance
import com.example.bankingapp.network.TransactionApiService
import com.example.bankingapp.utils.ApiResult
import com.example.bankingapp.utils.TransactionStatus
import com.example.bankingapp.utils.TransactionType
import retrofit2.Response

class TransactionRepositoryImpl(
    private val transactionApiService: TransactionApiService
): TransactionRepository {

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

    override suspend fun getTransactionByCustomer(transactionId: Long): ApiResult<TransactionResponseDto> {
        val result = transactionApiService.getTransactionByCustomer(transactionId)
        return helper(result)
    }

    override suspend fun transferFundByCustomer(transactionRequestDto: TransactionRequestDto): ApiResult<TransactionResponseDto> {
        val result = transactionApiService.transferFundByCustomer(transactionRequestDto)
        Log.d("rudra", result.toString())
        return helper(result)
    }

    override suspend fun getAllTransactionsByCustomer(
        page: Int?,
        size: Int?,
        transactionStatus: TransactionStatus?,
        transactionType: TransactionType?,
        fromDate: String?,
        toDate: String?
    ): ApiResult<TransactionPagedResultDto> {
        val result = transactionApiService.getAllTransactionsByCustomer(
            page = page,
            size = size,
            transactionStatus = transactionStatus,
            transactionType = transactionType,
            fromDate = fromDate,
            toDate = toDate
        )

        return helper(result)
    }

    override suspend fun getTransactionByEmployee(transactionId: Long): ApiResult<TransactionResponseDto> {
        val result = transactionApiService.getTransactionByEmployee(transactionId)
        return helper(result)
    }

    override suspend fun transferFundByEmployee(transactionRequestDto: TransactionRequestDto): ApiResult<TransactionResponseDto> {
        val result = transactionApiService.transferFundByEmployee(transactionRequestDto)
        return helper(result)
    }

    override suspend fun getAllTransactionsByEmployee(
        customerId: Long,
        page: Int?,
        size: Int?,
        transactionStatus: TransactionStatus?,
        transactionType: TransactionType?,
        fromDate: String?,
        toDate: String?
    ): ApiResult<TransactionPagedResultDto> {
        val result = transactionApiService.getAllTransactionsByEmployee(
            customerId = customerId,
            page = page,
            size = size,
            transactionStatus = transactionStatus,
            transactionType = transactionType,
            fromDate = fromDate,
            toDate = toDate
        )
        return helper(result)
    }
}
package com.example.bankingapp.repository.transaction

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.bankingapp.models.exception.ErrorResponse
import com.example.bankingapp.models.transactions.TransactionRequestDto
import com.example.bankingapp.models.transactions.TransactionResponseDto
import com.example.bankingapp.models.transactions.TransactionSummary
import com.example.bankingapp.network.RetrofitInstance
import com.example.bankingapp.network.TransactionApiService
import com.example.bankingapp.paging.MyPagingSource
import com.example.bankingapp.utils.ApiResult
import com.example.bankingapp.utils.Constants
import com.example.bankingapp.utils.TransactionStatus
import com.example.bankingapp.utils.TransactionType
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
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
        return helper(result)
    }

    override fun getAllTransactionsByCustomer(
        transactionStatus: TransactionStatus?,
        transactionType: TransactionType?,
        fromDate: String?,
        toDate: String?
    ): Flow<PagingData<TransactionSummary>> {
        return Pager(
            config = PagingConfig(
                pageSize = Constants.PAGE_SIZE
            ),
            pagingSourceFactory = {
                MyPagingSource(
                    fetchData = { page ->
                        val response = transactionApiService.getAllTransactionsByCustomer(
                            page = page,
                            transactionStatus = transactionStatus,
                            transactionType = transactionType,
                            fromDate = fromDate,
                            toDate = toDate
                        )

                        if(response.isSuccessful){
                            response.body() ?: throw Exception("Response body is null")
                        } else {
                            throw HttpException(response)
                        }
                    }
                )
            }
        ).flow
    }

    override suspend fun getTransactionByEmployee(transactionId: Long): ApiResult<TransactionResponseDto> {
        val result = transactionApiService.getTransactionByEmployee(transactionId)
        return helper(result)
    }

    override suspend fun transferFundByEmployee(transactionRequestDto: TransactionRequestDto): ApiResult<TransactionResponseDto> {
        val result = transactionApiService.transferFundByEmployee(transactionRequestDto)
        return helper(result)
    }

    override fun getAllTransactionsByEmployee(
        customerId: Long,
        transactionStatus: TransactionStatus?,
        transactionType: TransactionType?,
        fromDate: String?,
        toDate: String?
    ): Flow<PagingData<TransactionSummary>> {
        return Pager(
            config = PagingConfig(
                pageSize = Constants.PAGE_SIZE
            ),
            pagingSourceFactory = {
                MyPagingSource(
                    fetchData = { page ->
                        val result = transactionApiService.getAllTransactionsByEmployee(
                            page = page,
                            customerId = customerId,
                            transactionType = transactionType,
                            transactionStatus = transactionStatus,
                            fromDate = fromDate,
                            toDate = toDate
                        )

                        if(result.isSuccessful){
                            result.body() ?: throw Exception("Response body is null")
                        } else {
                            throw HttpException(result)
                        }
                    }
                )
            }
        ).flow
    }

}
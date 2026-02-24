package com.example.bankingapp.repository.transaction

import androidx.paging.PagingData
import com.example.bankingapp.models.PagedResponse
import com.example.bankingapp.models.transactions.TransactionRequestDto
import com.example.bankingapp.models.transactions.TransactionResponseDto
import com.example.bankingapp.models.transactions.TransactionSummary
import com.example.bankingapp.utils.ApiResult
import com.example.bankingapp.utils.TransactionStatus
import com.example.bankingapp.utils.TransactionType
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {

    suspend fun getTransactionByCustomer(transactionId: Long): ApiResult<TransactionResponseDto>

    suspend fun transferFundByCustomer(transactionRequestDto: TransactionRequestDto): ApiResult<TransactionResponseDto>

    fun getAllTransactionsByCustomer(
        transactionStatus: TransactionStatus? = null, transactionType: TransactionType? = null,
        fromDate: String? = null, toDate: String? = null
    ): Flow<PagingData<TransactionSummary>>

    suspend fun getTransactionByEmployee(transactionId: Long): ApiResult<TransactionResponseDto>

    suspend fun transferFundByEmployee(transactionRequestDto: TransactionRequestDto): ApiResult<TransactionResponseDto>

    fun getAllTransactionsByEmployee(
        customerId: Long, transactionStatus: TransactionStatus? = null,
        transactionType: TransactionType? = null, fromDate: String? = null, toDate: String? = null
    ): Flow<PagingData<TransactionSummary>>
}
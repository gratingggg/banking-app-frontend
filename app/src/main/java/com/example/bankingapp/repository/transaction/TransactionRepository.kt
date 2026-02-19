package com.example.bankingapp.repository.transaction

import com.example.bankingapp.models.transactions.TransactionPagedResultDto
import com.example.bankingapp.models.transactions.TransactionRequestDto
import com.example.bankingapp.models.transactions.TransactionResponseDto
import com.example.bankingapp.utils.ApiResult
import com.example.bankingapp.utils.TransactionStatus
import com.example.bankingapp.utils.TransactionType

interface TransactionRepository {

    suspend fun getTransactionByCustomer(transactionId: Long): ApiResult<TransactionResponseDto>

    suspend fun transferFundByCustomer(transactionRequestDto: TransactionRequestDto): ApiResult<TransactionResponseDto>

    suspend fun getAllTransactionsByCustomer(
        page: Int? = null, size: Int? = null,
        transactionStatus: TransactionStatus? = null, transactionType: TransactionType? = null,
        fromDate: String? = null, toDate: String? = null
    ): ApiResult<TransactionPagedResultDto>

    suspend fun getTransactionByEmployee(transactionId: Long): ApiResult<TransactionResponseDto>

    suspend fun transferFundByEmployee(transactionRequestDto: TransactionRequestDto): ApiResult<TransactionResponseDto>

    suspend fun getAllTransactionsByEmployee(
        customerId: Long, page: Int? = null, size: Int? = null,
        transactionStatus: TransactionStatus? = null, transactionType: TransactionType? = null,
        fromDate: String? = null, toDate: String? = null
    ): ApiResult<TransactionPagedResultDto>
}
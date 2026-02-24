package com.example.bankingapp.repository.account

import androidx.paging.PagingData
import com.example.bankingapp.models.PagedResponse
import com.example.bankingapp.models.account.AccountBalanceResponseDto
import com.example.bankingapp.models.account.AccountRequestDto
import com.example.bankingapp.models.account.AccountResponseDto
import com.example.bankingapp.models.account.AccountSummaryDto
import com.example.bankingapp.models.transactions.TransactionSummary
import com.example.bankingapp.utils.ApiResult
import com.example.bankingapp.utils.TransactionStatus
import com.example.bankingapp.utils.TransactionType
import kotlinx.coroutines.flow.Flow

interface CustomerAccountRepository {
    suspend fun getAllAccountsByCustomer(): ApiResult<List<AccountSummaryDto>>

    suspend fun getParticularAccountByCustomer(accountId: Long): ApiResult<AccountResponseDto>

    fun getAllAccountTransactionsByCustomer(
        accountId: Long,
        transactionStatus: TransactionStatus? = null,
        transactionType: TransactionType? = null,
        fromDate: String? = null,
        toDate: String? = null
    ): Flow<PagingData<TransactionSummary>>

    suspend fun createAccountByCustomer(accountRequestDto: AccountRequestDto): ApiResult<AccountResponseDto>

    suspend fun deleteAccountByCustomer(accountId: Long): ApiResult<AccountResponseDto>

    suspend fun getBalanceByCustomer(accountId: Long): ApiResult<AccountBalanceResponseDto>
}
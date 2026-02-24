package com.example.bankingapp.repository.account

import androidx.paging.PagingData
import com.example.bankingapp.models.PagedResponse
import com.example.bankingapp.models.account.AccountBalanceResponseDto
import com.example.bankingapp.models.account.AccountRequestDto
import com.example.bankingapp.models.account.AccountResponseDto
import com.example.bankingapp.models.account.AccountSummaryDto
import com.example.bankingapp.models.transactions.TransactionResponseDto
import com.example.bankingapp.models.transactions.TransactionSummary
import com.example.bankingapp.utils.ApiResult
import com.example.bankingapp.utils.TransactionStatus
import com.example.bankingapp.utils.TransactionType
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal

interface EmployeeAccountRepository {
    suspend fun getAllAccountsByEmployee(customerId: Long): ApiResult<List<AccountSummaryDto>>

    suspend fun getParticularAccountByEmployee(accountId: Long): ApiResult<AccountResponseDto>

    suspend fun createAccountByEmployee(
        customerId: Long,
        accountRequestDto: AccountRequestDto
    ): ApiResult<AccountResponseDto>

    suspend fun deleteAccountByEmployee(accountId: Long): ApiResult<AccountResponseDto>

    suspend fun getAllAccountTransactionByEmployee(
        accountId: Long,
        transactionStatus: TransactionStatus? = null,
        transactionType: TransactionType? = null,
        fromDate: String? = null, toDate: String? = null
    ): Flow<PagingData<TransactionSummary>>

    suspend fun getAccountBalanceByEmployee(accountId: Long): ApiResult<AccountBalanceResponseDto>

    suspend fun deposit(accountId: Long, amount: BigDecimal): ApiResult<TransactionResponseDto>

    suspend fun withdraw(accountId: Long, amount: BigDecimal): ApiResult<TransactionResponseDto>
}
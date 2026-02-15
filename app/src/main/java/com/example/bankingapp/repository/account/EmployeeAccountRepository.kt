package com.example.bankingapp.repository.account

import com.example.bankingapp.models.transactions.TransactionPagedResultDto
import com.example.bankingapp.models.account.AccountBalanceResponseDto
import com.example.bankingapp.models.account.AccountRequestDto
import com.example.bankingapp.models.account.AccountResponseDto
import com.example.bankingapp.models.account.AccountSummaryDto
import com.example.bankingapp.models.transactions.TransactionResponseDto
import com.example.bankingapp.utils.ApiResult
import com.example.bankingapp.utils.TransactionStatus
import com.example.bankingapp.utils.TransactionType
import java.math.BigDecimal

interface EmployeeAccountRepository {
    suspend fun getAllAccountsByEmployee(customerId: Long): ApiResult<List<AccountSummaryDto>>

    suspend fun getParticularAccountByEmployee(accountId: Long): ApiResult<AccountResponseDto>

    suspend fun createAccountByEmployee(customerId: Long, accountRequestDto: AccountRequestDto): ApiResult<AccountResponseDto>

    suspend fun deleteAccountByEmployee(accountId: Long): ApiResult<AccountResponseDto>

    suspend fun getAllAccountTransactionByEmployee(accountId: Long, path: Int? = null, size: Int? = null,
                                                   transactionStatus: TransactionStatus? = null, transactionType: TransactionType? = null,
                                                   fromDate: String? = null, toDate: String? = null
    ): ApiResult<TransactionPagedResultDto>

    suspend fun getAllTransactionsByEmployee(
        customerId: Long, path: Int? = null, size: Int? = null,
        transactionStatus: TransactionStatus? = null, transactionType: TransactionType? = null,
        fromDate: String? = null, toDate: String? = null
    ): ApiResult<TransactionPagedResultDto>

    suspend fun getAccountBalanceByEmployee(accountId: Long): ApiResult<AccountBalanceResponseDto>

    suspend fun deposit(accountId: Long, amount: BigDecimal): ApiResult<TransactionResponseDto>

    suspend fun withdraw(accountId: Long, amount: BigDecimal): ApiResult<TransactionResponseDto>
}
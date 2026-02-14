package com.example.bankingapp.repository.account

import com.example.bankingapp.models.PagedResultDto
import com.example.bankingapp.models.account.AccountBalanceResponseDto
import com.example.bankingapp.models.account.AccountRequestDto
import com.example.bankingapp.models.account.AccountResponseDto
import com.example.bankingapp.models.account.AccountSummaryDto
import com.example.bankingapp.models.transactions.TransactionResponseDto
import com.example.bankingapp.utils.ApiResult
import com.example.bankingapp.utils.TransactionStatus
import com.example.bankingapp.utils.TransactionType

interface CustomerAccountRepository {
    suspend fun getAllAccountsByCustomer(): ApiResult<List<AccountSummaryDto>>

    suspend fun getParticularAccountByCustomer(accountId: Long): ApiResult<AccountResponseDto>

    suspend fun getAllAccountTransactionsByCustomer(accountId: Long, path: Int? = null, size: Int? = null,
                                                    transactionStatus: TransactionStatus? = null, transactionType: TransactionType? = null,
                                                    fromDate: String? = null, toDate: String? = null): ApiResult<PagedResultDto<TransactionResponseDto>>

    suspend fun createAccountByCustomer(accountRequestDto: AccountRequestDto): ApiResult<AccountResponseDto>

    suspend fun deleteAccountByCustomer(accountId: Long): ApiResult<AccountResponseDto>

    suspend fun getBalanceByCustomer(accountId: Long): ApiResult<AccountBalanceResponseDto>
}
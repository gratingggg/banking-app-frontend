package com.example.bankingapp.repository.account

import android.R.attr.path
import android.util.Log
import com.example.bankingapp.models.transactions.TransactionPagedResultDto
import com.example.bankingapp.models.account.AccountBalanceResponseDto
import com.example.bankingapp.models.account.AccountRequestDto
import com.example.bankingapp.models.account.AccountResponseDto
import com.example.bankingapp.models.account.AccountSummaryDto
import com.example.bankingapp.models.exception.ErrorResponse
import com.example.bankingapp.models.transactions.TransactionResponseDto
import com.example.bankingapp.network.AccountApiService
import com.example.bankingapp.network.RetrofitInstance
import com.example.bankingapp.utils.ApiResult
import com.example.bankingapp.utils.TransactionStatus
import com.example.bankingapp.utils.TransactionType
import retrofit2.Response
import java.math.BigDecimal

class AccountRepositoryImpl(
    private val accountApiService: AccountApiService
) : CustomerAccountRepository, EmployeeAccountRepository {

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

    override suspend fun getAllAccountsByCustomer(): ApiResult<List<AccountSummaryDto>> {
        val result = accountApiService.getAllAccountsByCustomer()
        return helper(result)
    }

    override suspend fun getParticularAccountByCustomer(accountId: Long): ApiResult<AccountResponseDto> {
        val result = accountApiService.getParticularAccountsByCustomer(accountId)
        return helper(result)
    }

    override suspend fun getAllAccountTransactionsByCustomer(
        accountId: Long,
        page: Int?,
        size: Int?,
        transactionStatus: TransactionStatus?,
        transactionType: TransactionType?,
        fromDate: String?,
        toDate: String?
    ): ApiResult<TransactionPagedResultDto> {
        val result = accountApiService.getAllAccountTransactionsByCustomer(
            accountId = accountId,
            page = page,
            size = size,
            transactionStatus = transactionStatus,
            transactionType = transactionType,
            fromDate = fromDate,
            toDate = toDate
        )
        return helper(result)
    }

    override suspend fun createAccountByCustomer(accountRequestDto: AccountRequestDto): ApiResult<AccountResponseDto> {
        val result = accountApiService.createAccountByCustomer(accountRequestDto)
        return helper(result)
    }

    override suspend fun deleteAccountByCustomer(accountId: Long): ApiResult<AccountResponseDto> {
        val result = accountApiService.deleteAccountByCustomer(accountId)
        return helper(result)
    }

    override suspend fun getBalanceByCustomer(accountId: Long): ApiResult<AccountBalanceResponseDto> {
        val result = accountApiService.getBalanceByCustomer(accountId)
        return helper(result)
    }

    override suspend fun getAllAccountsByEmployee(customerId: Long): ApiResult<List<AccountSummaryDto>> {
        val result = accountApiService.getAllAccountsByEmployee(customerId)
        return helper(result)
    }

    override suspend fun getParticularAccountByEmployee(accountId: Long): ApiResult<AccountResponseDto> {
        val result = accountApiService.getParticularAccountByEmployee(accountId)
        return helper(result)
    }

    override suspend fun createAccountByEmployee(
        customerId: Long,
        accountRequestDto: AccountRequestDto
    ): ApiResult<AccountResponseDto> {
        val result = accountApiService.createAccountByEmployee(customerId, accountRequestDto)
        return helper(result)
    }

    override suspend fun deleteAccountByEmployee(accountId: Long): ApiResult<AccountResponseDto> {
        val result = accountApiService.deleteAccountByEmployee(accountId)
        return helper(result)
    }

    override suspend fun getAllAccountTransactionByEmployee(
        accountId: Long,
        page: Int?,
        size: Int?,
        transactionStatus: TransactionStatus?,
        transactionType: TransactionType?,
        fromDate: String?,
        toDate: String?
    ): ApiResult<TransactionPagedResultDto> {
        val result = accountApiService.getAllAccountTransactionsByEmployee(
            accountId = accountId,
            page = page,
            size = size,
            transactionStatus = transactionStatus,
            transactionType = transactionType,
            fromDate = fromDate,
            toDate = toDate
        )
        return helper(result)
    }

    override suspend fun getAccountBalanceByEmployee(accountId: Long): ApiResult<AccountBalanceResponseDto> {
        val result = accountApiService.getAccountBalanceByEmployee(accountId)
        return helper(result)
    }

    override suspend fun deposit(
        accountId: Long,
        amount: BigDecimal
    ): ApiResult<TransactionResponseDto> {
        val result = accountApiService.deposit(accountId, amount)
        return helper(result)
    }

    override suspend fun withdraw(
        accountId: Long,
        amount: BigDecimal
    ): ApiResult<TransactionResponseDto> {
        val result = accountApiService.withdraw(accountId, amount)
        return helper(result)
    }
}


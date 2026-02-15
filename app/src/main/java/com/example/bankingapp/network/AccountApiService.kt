package com.example.bankingapp.network

import com.example.bankingapp.models.transactions.TransactionPagedResultDto
import com.example.bankingapp.models.account.AccountBalanceResponseDto
import com.example.bankingapp.models.account.AccountRequestDto
import com.example.bankingapp.models.account.AccountResponseDto
import com.example.bankingapp.models.account.AccountSummaryDto
import com.example.bankingapp.models.transactions.TransactionResponseDto
import com.example.bankingapp.utils.Endpoints
import com.example.bankingapp.utils.TransactionStatus
import com.example.bankingapp.utils.TransactionType
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import java.math.BigDecimal

interface AccountApiService {
    @GET(Endpoints.CUSTOMER_ACCOUNTS_ALL)
    suspend fun getAllAccountsByCustomer(): Response<List<AccountSummaryDto>>

    @GET(Endpoints.CUSTOMER_ACCOUNT_PARTICULAR)
    suspend fun getParticularAccountsByCustomer(
        @Path("accountId") accountId: Long
    ): Response<AccountResponseDto>

    @GET(Endpoints.CUSTOMER_ACCOUNT_TRANSACTION_ALL)
    suspend fun getAllAccountTransactionsByCustomer(
        @Path("accountId") accountId: Long,
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null,
        @Query("transactionStatus") transactionStatus: TransactionStatus? = null,
        @Query("transactionType") transactionType: TransactionType? = null,
        @Query("fromDate") fromDate: String? = null,
        @Query("toDate") toDate: String? = null
    ): Response<TransactionPagedResultDto>

    @POST(Endpoints.CUSTOMER_ACCOUNT_CREATE)
    suspend fun createAccountByCustomer(
        @Body accountRequestDto: AccountRequestDto
    ): Response<AccountResponseDto>

    @POST(Endpoints.CUSTOMER_ACCOUNT_DELETE)
    suspend fun deleteAccountByCustomer(
        @Path("accountId") accountId: Long
    ): Response<AccountResponseDto>

    @GET(Endpoints.CUSTOMER_ACCOUNT_BALANCE)
    suspend fun getBalanceByCustomer(
        @Path("accountId") accountId: Long
    ): Response<AccountBalanceResponseDto>

    @GET(Endpoints.EMPLOYEE_ACCOUNTS_ALL)
    suspend fun getAllAccountsByEmployee(
        @Path("customerId") customerId: Long
    ): Response<List<AccountSummaryDto>>

    @GET(Endpoints.EMPLOYEE_ACCOUNT_PARTICULAR)
    suspend fun getParticularAccountByEmployee(
        @Path("accountId") accountId: Long
    ): Response<AccountResponseDto>

    @POST(Endpoints.EMPLOYEE_ACCOUNT_CREATE)
    suspend fun createAccountByEmployee(
        @Path("customerId") customerId: Long,
        @Body accountRequestDto: AccountRequestDto
    ): Response<AccountResponseDto>

    @POST(Endpoints.EMPLOYEE_ACCOUNT_DELETE)
    suspend fun deleteAccountByEmployee(
        @Path("accountId") accountId: Long
    ): Response<AccountResponseDto>

    @GET(Endpoints.EMPLOYEE_ACCOUNT_TRANSACTIONS_ALL)
    suspend fun getAllAccountTransactionsByEmployee(
        @Path("accountId") accountId: Long,
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null,
        @Query("transactionStatus") transactionStatus: TransactionStatus? = null,
        @Query("transactionType") transactionType: TransactionType? = null,
        @Query("fromDate") fromDate: String? = null,
        @Query("toDate") toDate: String? = null
    ): Response<TransactionPagedResultDto>

    @GET(Endpoints.EMPLOYEE_ACCOUNT_BALANCE)
    suspend fun getAccountBalanceByEmployee(
        @Path("accountId") accountId: Long
    ): Response<AccountBalanceResponseDto>

    @GET(Endpoints.EMPLOYEE_CUSTOMER_TRANSACTION_ALL)
    suspend fun getAllTransactionByEmployee(
        @Path("customerId") customerId: Long,
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null,
        @Query("transactionStatus") transactionStatus: TransactionStatus? = null,
        @Query("transactionType") transactionType: TransactionType? = null,
        @Query("fromDate") fromDate: String? = null,
        @Query("toDate") toDate: String? = null
    ): Response<TransactionPagedResultDto>

    @POST(Endpoints.EMPLOYEE_ACCOUNT_DEPOSIT)
    suspend fun deposit(
        @Path("accountId") accountId: Long,
        @Query("fund") amount: BigDecimal
    ): Response<TransactionResponseDto>

    @POST(Endpoints.EMPLOYEE_ACCOUNT_WITHDRAWAL)
    suspend fun withdraw(
        @Path("accountId") accountId: Long,
        @Query("fund") amount: BigDecimal
    ): Response<TransactionResponseDto>
}
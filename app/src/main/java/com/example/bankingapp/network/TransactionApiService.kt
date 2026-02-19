package com.example.bankingapp.network

import com.example.bankingapp.models.transactions.TransactionPagedResultDto
import com.example.bankingapp.models.transactions.TransactionRequestDto
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

interface TransactionApiService {
    @GET(Endpoints.TRANSACTION_CUSTOMER)
    suspend fun getTransactionByCustomer(@Path("transactionId") transactionId: Long)
    : Response<TransactionResponseDto>

    @GET(Endpoints.TRANSACTION_EMPLOYEE)
    suspend fun getTransactionByEmployee(@Path("transactionId") transactionId: Long)
    : Response<TransactionResponseDto>

    @POST(Endpoints.TRANSACTIONS_CUSTOMER_TRANSFER)
    suspend fun transferFundByCustomer(@Body transactionRequestDto: TransactionRequestDto)
    : Response<TransactionResponseDto>

    @POST(Endpoints.TRANSACTIONS_EMPLOYEE_TRANSFER)
    suspend fun transferFundByEmployee(@Body transactionRequestDto: TransactionRequestDto)
            : Response<TransactionResponseDto>

    @GET(Endpoints.CUSTOMER_TRANSACTION_ALL)
    suspend fun getAllTransactionsByCustomer(
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null,
        @Query("transactionStatus") transactionStatus: TransactionStatus? = null,
        @Query("transactionType") transactionType: TransactionType? = null,
        @Query("fromDate") fromDate: String? = null,
        @Query("toDate") toDate: String? = null
    ): Response<TransactionPagedResultDto>

    @GET(Endpoints.EMPLOYEE_CUSTOMER_TRANSACTION_ALL)
    suspend fun getAllTransactionsByEmployee(
        @Path("customerId") customerId: Long,
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null,
        @Query("transactionStatus") transactionStatus: TransactionStatus? = null,
        @Query("transactionType") transactionType: TransactionType? = null,
        @Query("fromDate") fromDate: String? = null,
        @Query("toDate") toDate: String? = null
    ): Response<TransactionPagedResultDto>
}
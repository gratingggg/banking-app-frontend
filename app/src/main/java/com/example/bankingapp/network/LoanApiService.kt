package com.example.bankingapp.network

import com.example.bankingapp.models.loan.LoanPagedResultDto
import com.example.bankingapp.models.loan.LoanRepaymentDto
import com.example.bankingapp.models.loan.LoanRequestDto
import com.example.bankingapp.models.loan.LoanResponseDto
import com.example.bankingapp.models.transactions.TransactionPagedResultDto
import com.example.bankingapp.models.transactions.TransactionResponseDto
import com.example.bankingapp.utils.Endpoints
import com.example.bankingapp.utils.LoanStatus
import com.example.bankingapp.utils.LoanType
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import java.time.LocalDate

interface LoanApiService {
    @POST(Endpoints.CUSTOMER_LOAN_APPLY)
    suspend fun createLoanByCustomer(@Body loanRequestDto: LoanRequestDto): Response<LoanResponseDto>

    @GET(Endpoints.CUSTOMER_LOAN_ALL)
    suspend fun getAllLoansByCustomer(
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null,
        @Query("status") loanStatus: LoanStatus? = null,
        @Query("type") loanType: LoanType? = null,
        @Query("fromDate") fromDate: LocalDate? = null,
        @Query("toDate") toDate: LocalDate? = null
    ): Response<LoanPagedResultDto>

    @POST(Endpoints.CUSTOMER_LOAN_REPAY)
    suspend fun repayLoanByCustomer(@Body loanRepaymentDto: LoanRepaymentDto): Response<TransactionResponseDto>

    @GET(Endpoints.CUSTOMER_LOAN_PARTICULAR)
    suspend fun getParticularLoanByCustomer(@Path("loanId") loanId: Long): Response<LoanResponseDto>

    @GET(Endpoints.CUSTOMER_LOAN_TRANSACTIONS)
    suspend fun getLoanTransactionsByCustomer(
        @Path("loanId") loanId: Long,
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null,
        @Query("status") loanStatus: LoanStatus? = null,
        @Query("type") loanType: LoanType? = null,
        @Query("fromDate") fromDate: LocalDate? = null,
        @Query("toDate") toDate: LocalDate? = null
    ): Response<TransactionPagedResultDto>

    @POST(Endpoints.EMPLOYEE_LOAN_APPLY)
    suspend fun createLoanByEmployee(
        @Path("accountId") accountId: Long,
        @Body loanRequestDto: LoanRequestDto
    ): Response<LoanResponseDto>

    @GET(Endpoints.EMPLOYEE_CUSTOMER_LOAN_ALL)
    suspend fun getAllLoansByEmployee(
        @Path("customerId") customerId: Long,
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null,
        @Query("status") loanStatus: LoanStatus? = null,
        @Query("type") loanType: LoanType? = null,
        @Query("fromDate") fromDate: LocalDate? = null,
        @Query("toDate") toDate: LocalDate? = null
    ): Response<LoanPagedResultDto>

    @GET(Endpoints.EMPLOYEE_LOAN_PARTICULAR)
    suspend fun getParticularLoanBYEmployee(@Path("loanId") loanId: Long): Response<LoanResponseDto>

    @GET(Endpoints.EMPLOYEE_LOAN_TRANSACTIONS)
    suspend fun getLoanTransactionsByEmployee(
        @Path("loanId") loanId: Long,
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null,
        @Query("status") loanStatus: LoanStatus? = null,
        @Query("type") loanType: LoanType? = null,
        @Query("fromDate") fromDate: LocalDate? = null,
        @Query("toDate") toDate: LocalDate? = null
    ): Response<TransactionPagedResultDto>

    @GET(Endpoints.EMPLOYEE_LOAN_ALL)
    suspend fun getAllLoans(
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null,
        @Query("status") loanStatus: LoanStatus? = null,
        @Query("type") loanType: LoanType? = null,
        @Query("fromDate") fromDate: LocalDate? = null,
        @Query("toDate") toDate: LocalDate? = null
    ): Response<LoanPagedResultDto>
}
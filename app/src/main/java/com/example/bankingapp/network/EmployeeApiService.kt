package com.example.bankingapp.network

import com.example.bankingapp.models.employee.EmployeeResponseDto
import com.example.bankingapp.models.loan.LoanPagedResultDto
import com.example.bankingapp.models.loan.LoanResponseDto
import com.example.bankingapp.models.transactions.TransactionResponseDto
import com.example.bankingapp.utils.Endpoints
import com.example.bankingapp.utils.LoanType
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface EmployeeApiService {
    @GET(Endpoints.EMPLOYEE_ME)
    suspend fun getMyDetails(): Response<EmployeeResponseDto>

    @POST(Endpoints.EMPLOYEE_LOANS_PROCESS)
    suspend fun processLoans(
        @Path("loanId") loanId: Long,
        @Query("action") action: String
    ): Response<LoanResponseDto>

    @POST(Endpoints.EMPLOYEE_LOANS_DISBURSE)
    suspend fun disburseLoans(@Path("loanId") loanId: Long): Response<TransactionResponseDto>
}
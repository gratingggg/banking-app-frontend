package com.example.bankingapp.network

import com.example.bankingapp.models.customer.CustomerResponseDto
import com.example.bankingapp.utils.Endpoints
import retrofit2.Response
import retrofit2.http.GET

interface CustomerApiService {
    @GET(Endpoints.CUSTOMER_ME)
    suspend fun getCustomerDetails(): Response<CustomerResponseDto>
}
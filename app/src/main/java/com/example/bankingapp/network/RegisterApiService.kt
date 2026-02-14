package com.example.bankingapp.network

import com.example.bankingapp.models.customer.CustomerRequestDto
import com.example.bankingapp.models.customer.CustomerResponseDto
import com.example.bankingapp.utils.Endpoints
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterApiService {
    @POST(Endpoints.REGISTER)
    suspend fun register(@Body customerRequestDto: CustomerRequestDto): Response<CustomerResponseDto>
}
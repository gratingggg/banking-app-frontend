package com.example.bankingapp.repository.register

import com.example.bankingapp.models.customer.CustomerRequestDto
import com.example.bankingapp.models.customer.CustomerResponseDto
import com.example.bankingapp.utils.ApiResult
import com.example.bankingapp.utils.Endpoints
import com.example.bankingapp.viewmodel.RegisterState
import retrofit2.http.POST

interface RegisterRepository {
    suspend fun register(customerRequestDto: CustomerRequestDto): ApiResult<CustomerResponseDto>
}
package com.example.bankingapp.repository.customer

import com.example.bankingapp.models.customer.CustomerResponseDto
import com.example.bankingapp.utils.ApiResult

interface CustomerRepository {
    suspend fun getMyDetails(): ApiResult<CustomerResponseDto>
}
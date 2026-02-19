package com.example.bankingapp.repository.register

import com.example.bankingapp.models.customer.CustomerRequestDto
import com.example.bankingapp.models.customer.CustomerResponseDto
import com.example.bankingapp.models.exception.ErrorResponse
import com.example.bankingapp.network.RegisterApiService
import com.example.bankingapp.network.RetrofitInstance
import com.example.bankingapp.utils.ApiResult

class RegisterRepositoryImpl(private val registerApiService: RegisterApiService)
    : RegisterRepository{
    override suspend fun register(customerRequestDto: CustomerRequestDto): ApiResult<CustomerResponseDto> {
        return try {
            val response = registerApiService.register(customerRequestDto)
            if(response.isSuccessful){
                ApiResult.Success(response.body()!!)
            } else {
                val error = RetrofitInstance.parseError(response.errorBody())
                ApiResult.Failure(error!!)
            }
        } catch (e: Exception){
            ApiResult.Failure(
                ErrorResponse(
                    message = e.message.toString(),
                    statusCode = 6969
                )
            )
        }
    }

}
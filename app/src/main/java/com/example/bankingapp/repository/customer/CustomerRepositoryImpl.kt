package com.example.bankingapp.repository.customer

import com.example.bankingapp.models.customer.CustomerResponseDto
import com.example.bankingapp.models.exception.ErrorResponse
import com.example.bankingapp.network.CustomerApiService
import com.example.bankingapp.network.RetrofitInstance
import com.example.bankingapp.utils.ApiResult
import retrofit2.Response

class CustomerRepositoryImpl(
    private val customerApiService: CustomerApiService
): CustomerRepository {
    override suspend fun getMyDetails(): ApiResult<CustomerResponseDto> {
        return try {
            val result = customerApiService.getCustomerDetails()

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
}
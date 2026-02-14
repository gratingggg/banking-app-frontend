package com.example.bankingapp.repository.auth

import com.example.bankingapp.models.exception.ErrorResponse
import com.example.bankingapp.models.login.LoginRequestDto
import com.example.bankingapp.models.login.LoginResponseDto
import com.example.bankingapp.network.AuthApiService
import com.example.bankingapp.network.RetrofitInstance
import com.example.bankingapp.utils.ApiResult
import com.example.bankingapp.utils.TokenManager
import com.example.bankingapp.viewmodel.LoginState

class AuthRepositoryImpl(
    private val apiService: AuthApiService
) : AuthRepository {
    override suspend fun login(loginRequestDto: LoginRequestDto): ApiResult<LoginResponseDto> {
        return try {
            val response = apiService.login(loginRequestDto)

            if(response.isSuccessful){
                response.body()?.token?.let {
                    TokenManager.token = it
                }
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
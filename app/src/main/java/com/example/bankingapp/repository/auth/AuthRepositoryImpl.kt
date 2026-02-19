package com.example.bankingapp.repository.auth

import com.example.bankingapp.SessionManager
import com.example.bankingapp.models.exception.ErrorResponse
import com.example.bankingapp.models.login.LoginRequestDto
import com.example.bankingapp.models.login.LoginResponseDto
import com.example.bankingapp.network.AuthApiService
import com.example.bankingapp.network.RetrofitInstance
import com.example.bankingapp.utils.ApiResult

class AuthRepositoryImpl(
    private val apiService: AuthApiService,
    private val sessionManager: SessionManager
) : AuthRepository {
    override suspend fun login(loginRequestDto: LoginRequestDto): ApiResult<LoginResponseDto> {
        return try {
            val response = apiService.login(loginRequestDto)

            if(response.isSuccessful){
                response.body()?.let { body ->
                   sessionManager.saveSession(
                       token = body.token,
                       username = body.username,
                       role = body.role
                   )
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
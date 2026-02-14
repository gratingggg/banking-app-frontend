package com.example.bankingapp.network

import com.example.bankingapp.models.login.LoginRequestDto
import com.example.bankingapp.models.login.LoginResponseDto
import com.example.bankingapp.utils.Endpoints
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    @POST(Endpoints.LOGIN)
    suspend fun login(@Body loginRequestDto: LoginRequestDto): Response<LoginResponseDto>
}
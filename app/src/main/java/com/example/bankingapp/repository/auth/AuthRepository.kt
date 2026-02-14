package com.example.bankingapp.repository.auth

import com.example.bankingapp.models.login.LoginRequestDto
import com.example.bankingapp.models.login.LoginResponseDto
import com.example.bankingapp.utils.ApiResult
import com.example.bankingapp.viewmodel.LoginState

interface AuthRepository {
    suspend fun login(loginRequestDto: LoginRequestDto): ApiResult<LoginResponseDto>
}
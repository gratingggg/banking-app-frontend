package com.example.bankingapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bankingapp.models.exception.ErrorResponse
import com.example.bankingapp.models.login.LoginRequestDto
import com.example.bankingapp.models.login.LoginResponseDto
import com.example.bankingapp.repository.auth.AuthRepository
import com.example.bankingapp.utils.ApiResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authRepository: AuthRepository
): ViewModel() {
    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    fun login(username: String, password: String){
        viewModelScope.launch {
            _loginState.value = LoginState.Loading

            val result = authRepository.login(
                LoginRequestDto(
                    username = username,
                    password = password
                )
            )

            when(result){
                is ApiResult.Success -> _loginState.value = LoginState.Success(result.data)
                is ApiResult.Failure -> _loginState.value = LoginState.Failure(result.error)
            }
        }
    }
}

sealed class LoginState{
    object Idle: LoginState()
    object Loading: LoginState()
    data class Success(val data: LoginResponseDto): LoginState()
    data class Failure(val error: ErrorResponse): LoginState()
}
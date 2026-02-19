package com.example.bankingapp.viewmodel

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
    private val _loginState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val loginState: StateFlow<LoginUiState> = _loginState

    fun login(username: String, password: String){
        viewModelScope.launch {
            _loginState.value = LoginUiState.Loading

            val result = authRepository.login(
                LoginRequestDto(
                    username = username,
                    password = password
                )
            )

            when(result){
                is ApiResult.Success -> _loginState.value = LoginUiState.Success(result.data)
                is ApiResult.Failure -> _loginState.value = LoginUiState.Failure(result.error)
            }
        }
    }
}

sealed class LoginUiState{
    object Idle: LoginUiState()
    object Loading: LoginUiState()
    data class Success(val data: LoginResponseDto): LoginUiState()
    data class Failure(val error: ErrorResponse): LoginUiState()
}
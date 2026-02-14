package com.example.bankingapp.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bankingapp.models.customer.CustomerRequestDto
import com.example.bankingapp.models.customer.CustomerResponseDto
import com.example.bankingapp.models.exception.ErrorResponse
import com.example.bankingapp.repository.register.RegisterRepository
import com.example.bankingapp.utils.ApiResult
import com.example.bankingapp.utils.Gender
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class RegisterViewModel(
    private val registerRepository: RegisterRepository
): ViewModel(){
    private var _registerState = MutableStateFlow<RegisterState>(RegisterState.Idle)
    val registerState: StateFlow<RegisterState> = _registerState

    fun register(name: String, email: String, phoneNumber: String,
                 dateOfBirthStr: String, address: String, genderStr: String,
                 username: String, password: String, aadharNo: String
    ){
        viewModelScope.launch {
            _registerState.value = RegisterState.Loading

            val gender = Gender.valueOf(genderStr)
            val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
            val dateOfBirth = LocalDate.parse(dateOfBirthStr, formatter)

            val result = registerRepository.register(
                CustomerRequestDto(
                    name = name,
                    email = email,
                    phoneNumber = phoneNumber,
                    dateOfBirth = dateOfBirth,
                    address = address,
                    gender = gender,
                    username = username,
                    password = password,
                    aadharNo = aadharNo
                )
            )

            when(result){
                is ApiResult.Failure -> _registerState.value = RegisterState.Failure(result.error)
                is ApiResult.Success -> _registerState.value = RegisterState.Success(result.data)
            }
        }
    }
}

sealed class RegisterState{
    object Idle: RegisterState()
    object Loading: RegisterState()
    data class Success(val data: CustomerResponseDto): RegisterState()
    data class Failure(val error: ErrorResponse): RegisterState()
}
package com.example.bankingapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bankingapp.models.customer.CustomerResponseDto
import com.example.bankingapp.models.exception.ErrorResponse
import com.example.bankingapp.repository.customer.CustomerRepository
import com.example.bankingapp.utils.ApiResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CustomerViewModel(
    private val customerRepository: CustomerRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(CustomerUiState())
    val uiState: StateFlow<CustomerUiState> = _uiState

    fun getMyDetails(){
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isGettingMyDetails = true
                )
            }

            _uiState.update {
                when(val result = customerRepository.getMyDetails()){
                    is ApiResult.Failure -> it.copy(
                        errorMyDetails = result.error,
                        isGettingMyDetails = false
                    )

                    is ApiResult.Success -> it.copy(
                        myDetails = result.data,
                        errorMyDetails = null,
                        isGettingMyDetails = false
                    )
                }
            }
        }
    }
}

data class CustomerUiState(
    val isGettingMyDetails: Boolean = false,
    val myDetails: CustomerResponseDto? = null,
    val errorMyDetails: ErrorResponse? = null
)
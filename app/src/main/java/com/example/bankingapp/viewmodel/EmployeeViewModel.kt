package com.example.bankingapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bankingapp.models.employee.EmployeeResponseDto
import com.example.bankingapp.models.exception.ErrorResponse
import com.example.bankingapp.models.loan.LoanResponseDto
import com.example.bankingapp.models.transactions.TransactionResponseDto
import com.example.bankingapp.repository.employee.EmployeeRepository
import com.example.bankingapp.utils.ApiResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EmployeeViewModel(
    private val employeeRepository: EmployeeRepository
): ViewModel(){
    private val _uiState = MutableStateFlow(EmployeeUiState())
    val uiState: StateFlow<EmployeeUiState> = _uiState

    fun getMyDetails(){
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isGettingMyDetails = true
                )
            }

            _uiState.update {
                when(val result = employeeRepository.getMyDetails()){
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

    fun processLoans(loanId: Long, action: String){
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isProcessingLoan = true
                )
            }

            _uiState.update {
                when(val result = employeeRepository.processLoan(loanId, action)){
                    is ApiResult.Failure -> it.copy(
                        errorProcessLoan = result.error,
                        isProcessingLoan = false
                    )

                    is ApiResult.Success -> it.copy(
                        processedLoan = result.data,
                        errorProcessLoan = null,
                        isProcessingLoan = false
                    )
                }
            }
        }
    }

    fun disburseLoans(loanId: Long){
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isDisbursingLoan = true
                )
            }

            _uiState.update {
                when(val result = employeeRepository.disburseLoan(loanId)){
                    is ApiResult.Failure -> it.copy(
                        errorDisburseLoan = result.error,
                        isDisbursingLoan = false
                    )

                    is ApiResult.Success -> it.copy(
                        disbursedLoan = result.data,
                        errorDisburseLoan = null,
                        isDisbursingLoan = false
                    )
                }
            }
        }
    }
}

data class EmployeeUiState(
    val isGettingMyDetails: Boolean = false,
    val isProcessingLoan: Boolean = false,
    val isDisbursingLoan: Boolean = false,

    val myDetails: EmployeeResponseDto? = null,
    val processedLoan: LoanResponseDto? = null,
    val disbursedLoan: TransactionResponseDto? = null,

    val errorMyDetails: ErrorResponse? = null,
    val errorProcessLoan: ErrorResponse? = null,
    val errorDisburseLoan: ErrorResponse? = null
)
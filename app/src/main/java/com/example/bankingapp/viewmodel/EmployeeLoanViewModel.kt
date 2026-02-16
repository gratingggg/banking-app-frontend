package com.example.bankingapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bankingapp.models.exception.ErrorResponse
import com.example.bankingapp.models.loan.LoanPagedResultDto
import com.example.bankingapp.models.loan.LoanRepaymentDto
import com.example.bankingapp.models.loan.LoanRequestDto
import com.example.bankingapp.models.loan.LoanResponseDto
import com.example.bankingapp.models.transactions.TransactionPagedResultDto
import com.example.bankingapp.models.transactions.TransactionResponseDto
import com.example.bankingapp.repository.loan.CustomerLoanRepository
import com.example.bankingapp.repository.loan.EmployeeLoanRepository
import com.example.bankingapp.utils.ApiResult
import com.example.bankingapp.utils.LoanStatus
import com.example.bankingapp.utils.LoanType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.time.LocalDate

class EmployeeLoanViewModel(
    private val employeeLoanRepository: EmployeeLoanRepository
): ViewModel(){
    private val _uiState = MutableStateFlow(EmployeeLoanUiState())
    val uiState: StateFlow<EmployeeLoanUiState> = _uiState

    fun createLoan(accountId: Long, tenureInMonths: Int,
                   loanTypeStr: String, principalAmountStr: String){
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isCreatingLoan = true
                )
            }

            val loanRequestDto = LoanRequestDto(
                accountId = accountId,
                tenureInMonths = tenureInMonths,
                loanType = LoanType.valueOf(loanTypeStr),
                principalAmount = BigDecimal(principalAmountStr)
            )

            _uiState.update {
                when(val result = employeeLoanRepository.createLoanByEmployee(accountId, loanRequestDto)){
                    is ApiResult.Failure -> it.copy(
                        errorCreateLoan = result.error,
                        isCreatingLoan = false
                    )

                    is ApiResult.Success -> it.copy(
                        createdLoan = result.data,
                        errorCreateLoan = null,
                        isCreatingLoan = false
                    )
                }
            }
        }
    }

    fun getAllCustomerLoans(customerId: Long, page: Int? = null, size: Int? = null, loanStatus: LoanStatus? = null,
        loanType: LoanType? = null, fromDate: LocalDate? = null, toDate: LocalDate? = null){
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoadingCustomerLoans = true
                )
            }

            val result = employeeLoanRepository.getAllCustomerLoansByEmployee(
                customerId = customerId, page = page, size = size, loanStatus = loanStatus,
                loanType = loanType, fromDate = fromDate, toDate = toDate
            )

            _uiState.update {
                when(result){
                    is ApiResult.Failure -> it.copy(
                        errorAllCustomerLoans = result.error,
                        isLoadingCustomerLoans = false
                    )

                    is ApiResult.Success -> it.copy(
                        allCustomerLoans = result.data,
                        errorAllCustomerLoans = null,
                        isLoadingCustomerLoans = false
                    )
                }
            }
        }
    }

    fun getParticularLoan(loanId: Long){
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoadingLoanDetails = true
                )
            }

            _uiState.update {
                when(val result = employeeLoanRepository.getParticularLoanByEmployee(loanId)){
                    is ApiResult.Failure -> it.copy(
                        errorSelectedLoan = result.error,
                        isLoadingLoanDetails = false
                    )

                    is ApiResult.Success -> it.copy(
                        selectedLoan = result.data,
                        errorSelectedLoan = null,
                        isLoadingLoanDetails = false
                    )
                }
            }
        }
    }

    fun getLoanTransactions(loanId: Long, page: Int? = null, size: Int? = null, loanStatus: LoanStatus? = null,
                            loanType: LoanType? = null, fromDate: LocalDate? = null, toDate: LocalDate? = null){
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoadingTransactions = true
                )
            }

            val result = employeeLoanRepository.getLoanTransactionsByEmployee(
                loanId = loanId, page = page, size = size, loanStatus = loanStatus,
                loanType = loanType, fromDate = fromDate, toDate = toDate
            )

            _uiState.update {
                when(result){
                    is ApiResult.Failure -> it.copy(
                        errorTransactions = result.error,
                        isLoadingTransactions = false
                    )

                    is ApiResult.Success -> it.copy(
                        loanTransactions = result.data,
                        errorTransactions = null,
                        isLoadingTransactions = false
                    )
                }
            }
        }
    }

    fun getAllLoans(page: Int? = null, size: Int? = null, loanStatus: LoanStatus? = null,
                            loanType: LoanType? = null, fromDate: LocalDate? = null, toDate: LocalDate? = null){
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoadingLoans = true
                )
            }

            val result = employeeLoanRepository.getAllLoansByEmployee(
                page = page, size = size, loanStatus = loanStatus,
                loanType = loanType, fromDate = fromDate, toDate = toDate
            )

            _uiState.update {
                when(result){
                    is ApiResult.Failure -> it.copy(
                        errorAllLoans = result.error,
                        isLoadingLoans = false
                    )

                    is ApiResult.Success -> it.copy(
                        allLoans = result.data,
                        errorAllLoans = null,
                        isLoadingLoans = false
                    )
                }
            }
        }
    }
}

data class EmployeeLoanUiState(
    val createdLoan: LoanResponseDto? = null,
    val allCustomerLoans: LoanPagedResultDto? = null,
    val loanTransactions: TransactionPagedResultDto? = null,
    val selectedLoan: LoanResponseDto? = null,
    val allLoans: LoanPagedResultDto? = null,


    val isCreatingLoan: Boolean = false,
    val isLoadingCustomerLoans: Boolean = false,
    val isLoadingTransactions: Boolean = false,
    val isLoadingLoanDetails: Boolean = false,
    val isLoadingLoans: Boolean = false,

    val errorCreateLoan: ErrorResponse? = null,
    val errorAllCustomerLoans: ErrorResponse? = null,
    val errorTransactions: ErrorResponse? = null,
    val errorSelectedLoan: ErrorResponse? = null,
    val errorAllLoans: ErrorResponse? = null
)
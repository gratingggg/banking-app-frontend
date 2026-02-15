package com.example.bankingapp.viewmodel

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
import com.example.bankingapp.utils.ApiResult
import com.example.bankingapp.utils.LoanStatus
import com.example.bankingapp.utils.LoanType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.time.LocalDate

class CustomerLoanViewModel(
    private val customerLoanRepository: CustomerLoanRepository
): ViewModel(){
    private val _uiState = MutableStateFlow(CustomerLoanUiState())
    val uiState: StateFlow<CustomerLoanUiState> = _uiState

    fun createLoan(loanRequestDto: LoanRequestDto){
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isCreatingLoan = true,
                    createdLoan = null
                )
            }

            _uiState.update {
                when(val result = customerLoanRepository.createLoan(loanRequestDto)){
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

    fun repayLoan(loanRepaymentDto: LoanRepaymentDto){
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isRepayingLoan = true,
                    repaidLoan = null
                )
            }

            _uiState.update {
                when(val result = customerLoanRepository.repayLoan(loanRepaymentDto)){
                    is ApiResult.Failure -> it.copy(
                        errorRepayLoan = result.error,
                        isRepayingLoan = false
                    )

                    is ApiResult.Success -> it.copy(
                        repaidLoan = result.data,
                        errorRepayLoan = null,
                        isRepayingLoan = false
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
                    allLoans = null,
                    isLoadingLoans = true
                )
            }

            val result = customerLoanRepository.getAllLoans(
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

    fun getParticularLoan(loanId: Long){
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoadingLoanDetails = true,
                    selectedLoan = null
                )
            }

            _uiState.update {
                when(val result = customerLoanRepository.getParticularLoan(loanId)){
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
                    isLoadingTransactions = true,
                    loanTransactions = null
                )
            }

            val result = customerLoanRepository.getAllTransactions(
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
}

data class CustomerLoanUiState(
    val createdLoan: LoanResponseDto? = null,
    val repaidLoan: TransactionResponseDto? = null,
    val allLoans: LoanPagedResultDto? = null,
    val loanTransactions: TransactionPagedResultDto? = null,
    val selectedLoan: LoanResponseDto? = null,

    val isCreatingLoan: Boolean = false,
    val isRepayingLoan: Boolean = false,
    val isLoadingLoans: Boolean = false,
    val isLoadingTransactions: Boolean = false,
    val isLoadingLoanDetails: Boolean = false,

    val errorCreateLoan: ErrorResponse? = null,
    val errorRepayLoan: ErrorResponse? = null,
    val errorAllLoans: ErrorResponse? = null,
    val errorTransactions: ErrorResponse? = null,
    val errorSelectedLoan: ErrorResponse? = null
)
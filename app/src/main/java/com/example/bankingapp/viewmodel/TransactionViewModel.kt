package com.example.bankingapp.viewmodel

import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bankingapp.SessionManager
import com.example.bankingapp.models.exception.ErrorResponse
import com.example.bankingapp.models.transactions.TransactionPagedResultDto
import com.example.bankingapp.models.transactions.TransactionRequestDto
import com.example.bankingapp.models.transactions.TransactionResponseDto
import com.example.bankingapp.repository.transaction.TransactionRepository
import com.example.bankingapp.utils.ApiResult
import com.example.bankingapp.utils.Role
import com.example.bankingapp.utils.TransactionStatus
import com.example.bankingapp.utils.TransactionType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class TransactionViewModel(
    private val transactionRepository: TransactionRepository,
    private val sessionManager: SessionManager
) : ViewModel() {
    private val _uiState = MutableStateFlow(TransactionUiState())
    val uiState: StateFlow<TransactionUiState> = _uiState

    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    fun getCustomerTransaction(transactionId: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoadingCustomerTransaction = true
                )
            }

            _uiState.update {
                when (val result =
                    transactionRepository.getTransactionByCustomer(transactionId.toLong())) {
                    is ApiResult.Failure -> it.copy(
                        errorCustomerTransaction = result.error,
                        isLoadingCustomerTransaction = false
                    )

                    is ApiResult.Success -> {
                        it.copy(
                            customerTransaction = result.data,
                            isLoadingCustomerTransaction = false,
                            errorCustomerTransaction = null
                        )
                    }
                }
            }
        }
    }

    fun getEmployeeTransaction(transactionId: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoadingEmployeeTransaction = true
                )
            }

            _uiState.update {
                when (val result =
                    transactionRepository.getTransactionByEmployee(transactionId.toLong())) {
                    is ApiResult.Failure -> it.copy(
                        errorEmployeeTransaction = result.error,
                        isLoadingEmployeeTransaction = false
                    )

                    is ApiResult.Success -> it.copy(
                        employeeTransaction = result.data,
                        isLoadingEmployeeTransaction = false,
                        errorEmployeeTransaction = null
                    )
                }
            }
        }
    }

    fun transferFundByCustomer(
        amountStr: String,
        fromAccountId: String,
        toAccountId: String? = null,
        loanId: String? = null,
        transactionTypeStr: String
    ) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isTransferringCustomer = true
                )
            }

            val transactionRequestDto = TransactionRequestDto(
                amount = BigDecimal(amountStr),
                fromAccountId = fromAccountId.toLong(),
                toAccountId = toAccountId?.toLong(),
                loanId = loanId?.toLong(),
                transactionType = TransactionType.valueOf(transactionTypeStr.uppercase())
            )

            _uiState.update {
                when (val result =
                    transactionRepository.transferFundByCustomer(transactionRequestDto)) {
                    is ApiResult.Failure -> it.copy(
                        errorCustomerTransfer = result.error,
                        isTransferringCustomer = false
                    )

                    is ApiResult.Success -> it.copy(
                        customerTransfer = result.data,
                        errorCustomerTransfer = null,
                        isTransferringCustomer = false
                    )
                }
            }
        }
    }

    fun transferFundByEmployee(
        amountStr: String,
        fromAccountId: String,
        toAccountId: String? = null,
        loanId: String? = null,
        transactionTypeStr: String
    ) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isTransferringEmployee = true
                )
            }

            val transactionRequestDto = TransactionRequestDto(
                amount = BigDecimal(amountStr),
                fromAccountId = fromAccountId.toLong(),
                toAccountId = toAccountId?.toLong(),
                loanId = loanId?.toLong(),
                transactionType = TransactionType.valueOf(transactionTypeStr.uppercase())
            )

            _uiState.update {
                when (val result =
                    transactionRepository.transferFundByEmployee(transactionRequestDto)) {
                    is ApiResult.Failure -> it.copy(
                        errorEmployeeTransfer = result.error,
                        isTransferringEmployee = false
                    )

                    is ApiResult.Success -> it.copy(
                        employeeTransfer = result.data,
                        isTransferringEmployee = false,
                        errorEmployeeTransfer = null
                    )
                }
            }
        }
    }

    fun getCustomerAllTransactions(
        page: Int? = null, size: Int? = null,
        transactionStatusStr: String? = null, transactionTypeStr: String? = null,
        fromDate: LocalDate? = null, toDate: LocalDate? = null
    ) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoadingCustomerAllTransactions = true
                )
            }

            val transactionStatus = if (transactionStatusStr == null) null else TransactionStatus.valueOf(transactionStatusStr)
            val transactionType = if(transactionTypeStr == null) null else TransactionType.valueOf(transactionTypeStr)

            _uiState.update {
                when (val result = transactionRepository.getAllTransactionsByCustomer(
                    page = page, size = size,
                    transactionStatus = transactionStatus, transactionType = transactionType,
                    fromDate = fromDate?.format(formatter), toDate = toDate?.format(formatter)
                )) {
                    is ApiResult.Failure -> it.copy(
                        errorCustomerAllTransactions = result.error,
                        isLoadingCustomerAllTransactions = false
                    )

                    is ApiResult.Success -> it.copy(
                        customerAllTransaction = result.data,
                        errorCustomerAllTransactions = null,
                        isLoadingCustomerAllTransactions = false
                    )
                }
            }
        }
    }

    fun getEmployeeAllTransaction(
        customerId: String, page: Int? = null, size: Int? = null,
        transactionStatusStr: String? = null, transactionTypeStr: String? = null,
        fromDate: LocalDate? = null, toDate: LocalDate? = null
    ) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoadingEmployeeAllTransactions = true
                )
            }

            val transactionStatus = if (transactionStatusStr == null) null else TransactionStatus.valueOf(transactionStatusStr)
            val transactionType = if(transactionTypeStr == null) null else TransactionType.valueOf(transactionTypeStr)


            _uiState.update {
                when (val result = transactionRepository.getAllTransactionsByEmployee(
                    customerId = customerId.toLong(), page = page, size = size,
                    transactionStatus = transactionStatus, transactionType = transactionType,
                    fromDate = fromDate?.format(formatter), toDate = toDate?.format(formatter)
                )) {
                    is ApiResult.Failure -> it.copy(
                        errorEmployeeAllTransactions = result.error,
                        isLoadingEmployeeAllTransactions = false
                    )

                    is ApiResult.Success -> it.copy(
                        employeeAllTransaction = result.data,
                        errorEmployeeAllTransactions = null,
                        isLoadingEmployeeAllTransactions = false
                    )
                }
            }
        }
    }
}

data class TransactionUiState(
    val isLoadingCustomerTransaction: Boolean = false,
    val isLoadingEmployeeTransaction: Boolean = false,
    val isLoadingCustomerAllTransactions: Boolean = false,
    val isLoadingEmployeeAllTransactions: Boolean = false,
    val isTransferringCustomer: Boolean = false,
    val isTransferringEmployee: Boolean = false,

    val customerTransaction: TransactionResponseDto? = null,
    val employeeTransaction: TransactionResponseDto? = null,
    val customerAllTransaction: TransactionPagedResultDto? = null,
    val employeeAllTransaction: TransactionPagedResultDto? = null,
    val customerTransfer: TransactionResponseDto? = null,
    val employeeTransfer: TransactionResponseDto? = null,

    val errorCustomerTransaction: ErrorResponse? = null,
    val errorEmployeeTransaction: ErrorResponse? = null,
    val errorCustomerAllTransactions: ErrorResponse? = null,
    val errorEmployeeAllTransactions: ErrorResponse? = null,
    val errorCustomerTransfer: ErrorResponse? = null,
    val errorEmployeeTransfer: ErrorResponse? = null
)
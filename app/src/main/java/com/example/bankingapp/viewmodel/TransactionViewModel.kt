package com.example.bankingapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.bankingapp.models.exception.ErrorResponse
import com.example.bankingapp.models.transactions.TransactionRequestDto
import com.example.bankingapp.models.transactions.TransactionResponseDto
import com.example.bankingapp.repository.transaction.TransactionRepository
import com.example.bankingapp.utils.ApiResult
import com.example.bankingapp.utils.Role
import com.example.bankingapp.utils.TransactionParams
import com.example.bankingapp.utils.TransactionStatus
import com.example.bankingapp.utils.TransactionType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.time.LocalDate

class TransactionViewModel(
    private val transactionRepository: TransactionRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(TransactionUiState())
    val uiState: StateFlow<TransactionUiState> = _uiState

    private val _transactionParams = MutableStateFlow<TransactionParams?>(null)
    @OptIn(ExperimentalCoroutinesApi::class)
    val transactions = _transactionParams.flatMapLatest { params ->
        if(params == null) emptyFlow()
        else if(params.customerId == null){
            transactionRepository.getAllTransactionsByCustomer(
                transactionStatus = params.transactionStatus,
                transactionType = params.transactionType,
                fromDate = params.fromDate,
                toDate = params.toDate
            )
        } else {
            transactionRepository.getAllTransactionsByEmployee(
                customerId = params.customerId,
                transactionStatus = params.transactionStatus,
                transactionType = params.transactionType,
                fromDate = params.fromDate,
                toDate = params.toDate
            )
        }
    }.cachedIn(viewModelScope)

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
        transactionStatusStr: String? = null, transactionTypeStr: String? = null,
        fromDateStr: String? = null
    ) {

        val transactionStatus =
            if (transactionStatusStr == null) null else TransactionStatus.valueOf(
                transactionStatusStr.uppercase()
            )
        val transactionType =
            if (transactionTypeStr == null) null else TransactionType.valueOf(transactionTypeStr.uppercase())

        val monthsToMinus: Long = when (fromDateStr) {
            "This month" -> 1
            "Last 90 days" -> 3
            "Last 180 days" -> 6
            else -> 0
        }

        val fromDate =
            if (monthsToMinus.toInt() == 0) null else LocalDate.now().minusMonths(monthsToMinus)
                .toString()

        _transactionParams.value = TransactionParams(
            transactionStatus = transactionStatus, transactionType = transactionType,
            fromDate = fromDate
        )

    }

    fun getEmployeeAllTransaction(
        customerId: String, transactionStatusStr: String? = null,
        transactionTypeStr: String? = null, fromDateStr: String? = null
    ) {

        val transactionStatus =
            if (transactionStatusStr == null) null else TransactionStatus.valueOf(
                transactionStatusStr.uppercase()
            )
        val transactionType =
            if (transactionTypeStr == null) null else TransactionType.valueOf(transactionTypeStr.uppercase())

        val monthsToMinus: Long = when (fromDateStr) {
            "This month" -> 1
            "Last 90 days" -> 3
            "Last 180 days" -> 6
            else -> 0
        }

        val fromDate =
            if (monthsToMinus.toInt() == 0) null else LocalDate.now().minusMonths(monthsToMinus)
                .toString()

        _transactionParams.value = TransactionParams(
            transactionStatus = transactionStatus, transactionType = transactionType,
            fromDate = fromDate, customerId = customerId.toLong()
        )
    }

    fun transferFund(
        amountStr: String,
        fromAccountId: String,
        toAccountId: String? = null,
        loanId: String? = null,
        transactionTypeStr: String,
        role: Role? = null
    ){
        role?.let {
            when(it){
                Role.CUSTOMER -> transferFundByCustomer(
                    amountStr = amountStr,
                    fromAccountId = fromAccountId,
                    toAccountId = toAccountId,
                    loanId = loanId,
                    transactionTypeStr = transactionTypeStr
                )

                Role.EMPLOYEE -> transferFundByEmployee(
                    amountStr = amountStr,
                    fromAccountId = fromAccountId,
                    toAccountId = toAccountId,
                    loanId = loanId,
                    transactionTypeStr = transactionTypeStr
                )
            }
        }
    }

    fun clearTransferState(){
        _uiState.update {
            it.copy(
                customerTransfer = null,
                employeeTransfer = null
            )
        }
    }
}

data class TransactionUiState(
    val isLoadingCustomerTransaction: Boolean = false,
    val isLoadingEmployeeTransaction: Boolean = false,
    val isTransferringCustomer: Boolean = false,
    val isTransferringEmployee: Boolean = false,

    val customerTransaction: TransactionResponseDto? = null,
    val employeeTransaction: TransactionResponseDto? = null,
    val customerTransfer: TransactionResponseDto? = null,
    val employeeTransfer: TransactionResponseDto? = null,

    val errorCustomerTransaction: ErrorResponse? = null,
    val errorEmployeeTransaction: ErrorResponse? = null,
    val errorCustomerTransfer: ErrorResponse? = null,
    val errorEmployeeTransfer: ErrorResponse? = null
)


package com.example.bankingapp.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bankingapp.models.PagedResultDto
import com.example.bankingapp.models.account.AccountBalanceResponseDto
import com.example.bankingapp.models.account.AccountRequestDto
import com.example.bankingapp.models.account.AccountResponseDto
import com.example.bankingapp.models.account.AccountSummaryDto
import com.example.bankingapp.models.exception.ErrorResponse
import com.example.bankingapp.models.transactions.TransactionResponseDto
import com.example.bankingapp.repository.account.CustomerAccountRepository
import com.example.bankingapp.utils.ApiResult
import com.example.bankingapp.utils.TransactionStatus
import com.example.bankingapp.utils.TransactionType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.publish
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CustomerAccountViewModel(
    private val customerAccountRepository: CustomerAccountRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(CustomerAccountUiState())
    val uiState: StateFlow<CustomerAccountUiState> = _uiState

    private val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

    fun getAllAccounts() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoadingAccounts = true,
                    error = null
                )
            }

            when (val result = customerAccountRepository.getAllAccountsByCustomer()) {
                is ApiResult.Failure -> _uiState.update {
                    it.copy(error = result.error)
                }

                is ApiResult.Success -> _uiState.update {
                    it.copy(
                        accountsList = result.data,
                        error = null
                    )
                }
            }
            _uiState.update {
                it.copy(isLoadingAccounts = false)
            }
        }
    }

    fun getParticularAccount(accountId: Long) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    selectedAccount = null,
                    isLoadingAccountDetails = true
                )
            }

            when (val result =
                customerAccountRepository.getParticularAccountByCustomer(accountId)) {
                is ApiResult.Failure -> _uiState.update {
                    it.copy(error = result.error)
                }

                is ApiResult.Success -> _uiState.update {
                    it.copy(
                        selectedAccount = result.data,
                        error = null
                    )
                }
            }

            _uiState.update {
                it.copy(isLoadingAccountDetails = false)
            }
        }
    }

    fun getAllAccountTransactions(
        accountId: Long, path: Int? = null, size: Int? = null,
        transactionStatus: TransactionStatus? = null, transactionType: TransactionType? = null,
        fromDate: LocalDate? = null, toDate: LocalDate? = null
    ) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    accountTransactions = null,
                    isLoadingTransactions = true
                )
            }

            when (val result = customerAccountRepository.getAllAccountTransactionsByCustomer(
                accountId = accountId, path = path, size = size,
                transactionStatus = transactionStatus, transactionType = transactionType,
                fromDate = fromDate?.format(formatter), toDate = toDate?.format(formatter)
            )) {
                is ApiResult.Failure -> _uiState.update {
                    it.copy(error = result.error)
                }

                is ApiResult.Success -> _uiState.update {
                    it.copy(
                        accountTransactions = result.data,
                        error = null
                    )
                }
            }

            _uiState.update {
                it.copy(isLoadingTransactions = false)
            }
        }
    }

    fun createAccount(accountRequestDto: AccountRequestDto) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    error = null,
                    isCreatingAccount = true,
                    isLoadingAccountDetails = true,
                    selectedAccount = null
                )
            }

            val result = customerAccountRepository.createAccountByCustomer(accountRequestDto)

            _uiState.update {
                val baseState = it.copy(
                    isCreatingAccount = false,
                    isLoadingAccountDetails = false
                )

                when (result) {
                    is ApiResult.Failure -> baseState.copy(
                        error = result.error
                    )

                    is ApiResult.Success -> baseState.copy(
                        selectedAccount = result.data,
                        error = null
                    )
                }
            }
        }
    }

    fun deleteAccount(accountId: Long) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isDeletingAccount = true,
                    isLoadingAccountDetails = true,
                    error = null
                )
            }

            val result = customerAccountRepository.deleteAccountByCustomer(accountId)

            _uiState.update {
                val baseState = it.copy(
                    isDeletingAccount = false,
                    isLoadingAccountDetails = false
                )

                when (result) {
                    is ApiResult.Failure -> baseState.copy(
                        error = result.error
                    )

                    is ApiResult.Success -> baseState.copy(
                        selectedAccount = result.data
                    )
                }
            }
        }
    }

    fun getAccountBalance(accountId: Long) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoadingBalance = true,
                    balance = null
                )
            }

            val result = customerAccountRepository.getBalanceByCustomer(accountId)

            _uiState.update {
                when(result) {
                    is ApiResult.Failure -> it.copy(
                        error = result.error,
                        isLoadingBalance = false
                    )
                    is ApiResult.Success -> it.copy(
                        balance = result.data,
                        isLoadingBalance = false,
                        error = null
                    )
                }
            }
        }
    }
}


data class CustomerAccountUiState(
    val accountsList: List<AccountSummaryDto> = emptyList<AccountSummaryDto>(),
    val selectedAccount: AccountResponseDto? = null,
    val accountTransactions: PagedResultDto<TransactionResponseDto>? = null,
    val balance: AccountBalanceResponseDto? = null,

    val isLoadingAccounts: Boolean = false,
    val isLoadingAccountDetails: Boolean = false,
    val isCreatingAccount: Boolean = false,
    val isDeletingAccount: Boolean = false,
    val isLoadingTransactions: Boolean = false,
    val isLoadingBalance: Boolean = false,

    val error: ErrorResponse? = null
)
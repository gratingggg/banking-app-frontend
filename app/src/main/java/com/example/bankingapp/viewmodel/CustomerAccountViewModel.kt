package com.example.bankingapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bankingapp.models.transactions.TransactionPagedResultDto
import com.example.bankingapp.models.account.AccountBalanceResponseDto
import com.example.bankingapp.models.account.AccountRequestDto
import com.example.bankingapp.models.account.AccountResponseDto
import com.example.bankingapp.models.account.AccountSummaryDto
import com.example.bankingapp.models.exception.ErrorResponse
import com.example.bankingapp.models.transactions.TransactionResponseDto
import com.example.bankingapp.repository.account.CustomerAccountRepository
import com.example.bankingapp.utils.AccountType
import com.example.bankingapp.utils.ApiResult
import com.example.bankingapp.utils.TransactionStatus
import com.example.bankingapp.utils.TransactionType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
                    isLoadingAccounts = true
                )
            }

            _uiState.update {
                when (val result = customerAccountRepository.getAllAccountsByCustomer()) {
                    is ApiResult.Failure -> it.copy(
                        errorAccountsList = result.error,
                        isLoadingAccounts = false
                    )


                    is ApiResult.Success -> it.copy(
                        accountsList = result.data,
                        errorAccountsList = null,
                        isLoadingAccounts = false
                    )

                }
            }
        }
    }

    fun getParticularAccount(accountId: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoadingSelectedAccountDetails = true
                )
            }

            _uiState.update {
                when (val result =
                    customerAccountRepository.getParticularAccountByCustomer(accountId.toLong())) {
                    is ApiResult.Failure -> {
                        it.copy(
                            errorSelectedAccount = result.error,
                            isLoadingSelectedAccountDetails = false
                        )
                    }

                    is ApiResult.Success -> {
                        it.copy(
                            selectedAccount = result.data,
                            errorSelectedAccount = null,
                            isLoadingSelectedAccountDetails = false
                        )
                    }
                }
            }
        }
    }

    fun getAllAccountTransactions(
        accountId: String, path: Int? = null, size: Int? = null,
        transactionStatus: TransactionStatus? = null, transactionType: TransactionType? = null,
        fromDate: LocalDate? = null, toDate: LocalDate? = null
    ) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoadingTransactions = true
                )
            }

            _uiState.update {
                when (val result = customerAccountRepository.getAllAccountTransactionsByCustomer(
                    accountId = accountId.toLong(), path = path, size = size,
                    transactionStatus = transactionStatus, transactionType = transactionType,
                    fromDate = fromDate?.format(formatter), toDate = toDate?.format(formatter)
                )) {
                    is ApiResult.Failure -> it.copy(
                        errorGetTransaction = result.error,
                        isLoadingTransactions = false
                    )

                    is ApiResult.Success -> it.copy(
                        accountTransactions = result.data,
                        errorGetTransaction = null,
                        isLoadingTransactions = false
                    )
                }
            }
        }
    }

    fun createAccount(accountTypeStr: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isCreatingAccount = true
                )
            }

            val accountRequestDto = AccountRequestDto(
                accountType = AccountType.valueOf(accountTypeStr)
            )

            val result = customerAccountRepository.createAccountByCustomer(accountRequestDto)

            _uiState.update {

                when (result) {
                    is ApiResult.Failure -> it.copy(
                        errorCreatedAccount = result.error,
                        isCreatingAccount = false
                    )

                    is ApiResult.Success -> it.copy(
                        createdAccount = result.data,
                        errorCreatedAccount = null,
                        isCreatingAccount = false
                    )
                }
            }
        }
    }

    fun deleteAccount(accountId: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isDeletingAccount = true
                )
            }

            val result = customerAccountRepository.deleteAccountByCustomer(accountId.toLong())

            _uiState.update {
                when (result) {
                    is ApiResult.Failure -> it.copy(
                        errorDeletedAccount = result.error,
                        isDeletingAccount = false
                    )

                    is ApiResult.Success -> it.copy(
                        deletedAccount = result.data,
                        errorDeletedAccount = null,
                        isDeletingAccount = false
                    )
                }
            }
        }
    }

    fun getAccountBalance(accountId: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoadingBalance = true
                )
            }

            val result = customerAccountRepository.getBalanceByCustomer(accountId.toLong())

            _uiState.update {
                when (result) {
                    is ApiResult.Failure -> it.copy(
                        errorGetBalance = result.error,
                        isLoadingBalance = false
                    )

                    is ApiResult.Success -> it.copy(
                        balance = result.data,
                        errorGetBalance = null,
                        isLoadingBalance = false
                    )
                }
            }
        }
    }
}


data class CustomerAccountUiState(
    val accountsList: List<AccountSummaryDto> = emptyList<AccountSummaryDto>(),
    val selectedAccount: AccountResponseDto? = null,
    val createdAccount: AccountResponseDto? = null,
    val deletedAccount: AccountResponseDto? = null,
    val accountTransactions: TransactionPagedResultDto? = null,
    val balance: AccountBalanceResponseDto? = null,

    val isLoadingAccounts: Boolean = false,
    val isLoadingSelectedAccountDetails: Boolean = false,
    val isCreatingAccount: Boolean = false,
    val isDeletingAccount: Boolean = false,
    val isLoadingTransactions: Boolean = false,
    val isLoadingBalance: Boolean = false,

    val errorAccountsList: ErrorResponse? = null,
    val errorSelectedAccount: ErrorResponse? = null,
    val errorCreatedAccount: ErrorResponse? = null,
    val errorDeletedAccount: ErrorResponse? = null,
    val errorGetTransaction: ErrorResponse? = null,
    val errorGetBalance: ErrorResponse? = null
)
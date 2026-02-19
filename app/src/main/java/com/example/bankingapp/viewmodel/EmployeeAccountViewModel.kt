package com.example.bankingapp.viewmodel

import android.R.attr.path
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bankingapp.models.account.AccountBalanceResponseDto
import com.example.bankingapp.models.account.AccountRequestDto
import com.example.bankingapp.models.account.AccountResponseDto
import com.example.bankingapp.models.account.AccountSummaryDto
import com.example.bankingapp.models.exception.ErrorResponse
import com.example.bankingapp.models.transactions.TransactionPagedResultDto
import com.example.bankingapp.models.transactions.TransactionResponseDto
import com.example.bankingapp.repository.account.EmployeeAccountRepository
import com.example.bankingapp.utils.AccountType
import com.example.bankingapp.utils.ApiResult
import com.example.bankingapp.utils.TransactionStatus
import com.example.bankingapp.utils.TransactionType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class EmployeeAccountViewModel(
    private val employeeAccountRepository: EmployeeAccountRepository
): ViewModel(){
    private val _uiState = MutableStateFlow(EmployeeAccountUiState())
    val uiState: StateFlow<EmployeeAccountUiState> = _uiState

    private val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

    fun getAllAccounts(customerId: Long) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoadingAccounts = true
                )
            }

            _uiState.update {
                when (val result = employeeAccountRepository.getAllAccountsByEmployee(customerId)) {
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

    fun getParticularAccount(accountId: Long) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoadingSelectedAccountDetails = true
                )
            }

            _uiState.update {
                when (val result =
                    employeeAccountRepository.getParticularAccountByEmployee(accountId)) {
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
        accountId: Long, path: Int? = null, size: Int? = null,
        transactionStatus: TransactionStatus? = null, transactionType: TransactionType? = null,
        fromDate: LocalDate? = null, toDate: LocalDate? = null
    ) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoadingAccountTransactions = true
                )
            }

            _uiState.update {
                when (val result = employeeAccountRepository.getAllAccountTransactionByEmployee(
                    accountId = accountId, path = path, size = size,
                    transactionStatus = transactionStatus, transactionType = transactionType,
                    fromDate = fromDate?.format(formatter), toDate = toDate?.format(formatter)
                )) {
                    is ApiResult.Failure -> it.copy(
                        errorGetAccountTransaction = result.error,
                        isLoadingAccountTransactions = false
                    )

                    is ApiResult.Success -> it.copy(
                        accountTransactions = result.data,
                        errorGetAccountTransaction = null,
                        isLoadingAccountTransactions = false
                    )
                }
            }
        }
    }

    fun createAccount(customerId: Long, accountTypeStr: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isCreatingAccount = true
                )
            }

            val accountRequestDto = AccountRequestDto(
                accountType = AccountType.valueOf(accountTypeStr)
            )

            val result = employeeAccountRepository.createAccountByEmployee(customerId, accountRequestDto)

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

    fun deleteAccount(accountId: Long) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isDeletingAccount = true
                )
            }

            val result = employeeAccountRepository.deleteAccountByEmployee(accountId)

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

    fun getAccountBalance(accountId: Long) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoadingBalance = true
                )
            }

            val result = employeeAccountRepository.getAccountBalanceByEmployee(accountId)

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

    fun deposit(accountId: Long, amountStr: String){
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isDepositing = true
                )
            }

            val amount = BigDecimal(amountStr)
            val result = employeeAccountRepository.deposit(accountId, amount)

            _uiState.update {
                when(result){
                    is ApiResult.Failure -> it.copy(
                        errorDeposit = result.error,
                        isDepositing = false
                    )

                    is ApiResult.Success -> it.copy(
                        deposit = result.data,
                        errorDeposit = null,
                        isDepositing = false
                    )
                }
            }
        }
    }

    fun withdraw(accountId: Long, amountStr: String){
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isWithdrawing = true
                )
            }

            val amount = BigDecimal(amountStr)
            val result = employeeAccountRepository.withdraw(accountId, amount)

            _uiState.update {
                when(result){
                    is ApiResult.Failure -> it.copy(
                        errorWithdraw = result.error,
                        isWithdrawing = false
                    )

                    is ApiResult.Success -> it.copy(
                        withdraw = result.data,
                        errorWithdraw = null,
                        isWithdrawing = false
                    )
                }
            }
        }
    }
}

data class EmployeeAccountUiState(
    val accountsList: List<AccountSummaryDto> = emptyList<AccountSummaryDto>(),
    val selectedAccount: AccountResponseDto? = null,
    val createdAccount: AccountResponseDto? = null,
    val deletedAccount: AccountResponseDto? = null,
    val accountTransactions: TransactionPagedResultDto? = null,
    val customerTransactions: TransactionPagedResultDto? = null,
    val balance: AccountBalanceResponseDto? = null,
    val deposit: TransactionResponseDto? = null,
    val withdraw: TransactionResponseDto? = null,

    val isLoadingAccounts: Boolean = false,
    val isLoadingSelectedAccountDetails: Boolean = false,
    val isCreatingAccount: Boolean = false,
    val isDeletingAccount: Boolean = false,
    val isLoadingAccountTransactions: Boolean = false,
    val isLoadingCustomerTransactions: Boolean = false,
    val isLoadingBalance: Boolean = false,
    val isDepositing: Boolean = false,
    val isWithdrawing: Boolean = false,

    val errorAccountsList: ErrorResponse? = null,
    val errorSelectedAccount: ErrorResponse? = null,
    val errorCreatedAccount: ErrorResponse? = null,
    val errorDeletedAccount: ErrorResponse? = null,
    val errorGetAccountTransaction: ErrorResponse? = null,
    val errorGetBalance: ErrorResponse? = null,
    val errorGetCustomerTransactions: ErrorResponse? = null,
    val errorDeposit: ErrorResponse? = null,
    val errorWithdraw: ErrorResponse? = null
)
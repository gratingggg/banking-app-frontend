package com.example.bankingapp.testing

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import com.example.bankingapp.models.account.AccountRequestDto
import com.example.bankingapp.utils.AccountType
import com.example.bankingapp.viewmodel.EmployeeAccountViewModel


@Composable
fun TestingEmployeeAccountApi(viewModel: EmployeeAccountViewModel) {
    val state by viewModel.uiState.collectAsState()

    var accountId by remember {
        mutableLongStateOf(234)
    }

    val customerId: Long = 1

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TestingGetAllAccounts(
            isLoadingAccounts = state.isLoadingAccounts,
            errorAccountsList = state.errorAccountsList,
            accountsList = state.accountsList,
            onGetAllAccounts = {
                viewModel.getAllAccounts(customerId)
            }
        ) {
            accountId = it
        }

        TestingGetParticularAccount(
            isLoadingSelectedAccountDetails = state.isLoadingSelectedAccountDetails,
            errorSelectedAccount = state.errorSelectedAccount,
            selectedAccount = state.selectedAccount,
        ) {
            viewModel.getParticularAccount(accountId)
        }

        TestingGetAllAccountTransactions(
            isLoadingTransactions = state.isLoadingAccountTransactions,
            errorGetTransaction = state.errorGetAccountTransaction,
            accountTransactions = state.accountTransactions
        ) {
            viewModel.getAllAccountTransactions(accountId)
        }

        TestingCreateAccount(
            isCreatingAccount = state.isCreatingAccount,
            errorCreatedAccount = state.errorCreatedAccount,
            createdAccount = state.createdAccount,
            onCreateAccount = {
                viewModel.createAccount(
                    customerId = customerId,
                    accountTypeStr = "CURRENT"
                )
            }
        ) {
            accountId = it
        }

        TestingAccountDeletion(
            isDeletingAccount = state.isDeletingAccount,
            errorDeletedAccount = state.errorDeletedAccount,
            deletedAccount = state.deletedAccount
        ) {
            viewModel.deleteAccount(accountId)
        }

        TestingGetAccountBalance(
            isLoadingBalance = state.isLoadingBalance,
            errorGetBalance = state.errorGetBalance,
            balance = state.balance
        ) {
            viewModel.getAccountBalance(accountId)
        }

        TestingGetCustomerTransactionByEmployee(
            isLoadingTransactions = state.isLoadingCustomerTransactions,
            errorGetTransaction = state.errorGetCustomerTransactions,
            transactions = state.customerTransactions
        ) {
            viewModel.getCustomerTransactions(customerId)
        }

        TestingDeposit(
            isDepositing = state.isDepositing,
            errorDeposit = state.errorDeposit,
            deposit = state.deposit
        ) {
            viewModel.deposit(accountId, "2000")
        }

        TestingWithdraw(
            isWithdrawing = state.isWithdrawing,
            errorWithdraw = state.errorWithdraw,
            withdraw = state.withdraw
        ) {
            viewModel.withdraw(accountId, "1000")
        }
    }
}

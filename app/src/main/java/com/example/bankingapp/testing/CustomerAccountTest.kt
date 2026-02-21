package com.example.bankingapp.testing

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bankingapp.models.account.AccountRequestDto
import com.example.bankingapp.utils.AccountType
import com.example.bankingapp.viewmodel.CustomerAccountViewModel
import java.nio.file.WatchEvent

@Composable
fun TestingCustomerAccountApi(viewModel: CustomerAccountViewModel) {
    val state by viewModel.uiState.collectAsState()

    var accountId by remember {
        mutableLongStateOf(234)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "CUSTOMER ACCOUNT START",
            fontSize = 20.sp,
            modifier = Modifier.padding(16.dp)
        )
        TestingGetAllAccounts(
            isLoadingAccounts = state.isLoadingAccounts,
            errorAccountsList = state.errorAccountsList,
            accountsList = state.accountsList,
            onGetAllAccounts = {
                viewModel.getAllAccounts()
            }
        ) {
            accountId = it
        }

        TestingGetParticularAccount(
            isLoadingSelectedAccountDetails = state.isLoadingSelectedAccountDetails,
            errorSelectedAccount = state.errorSelectedAccount,
            selectedAccount = state.selectedAccount,
        ) {
            viewModel.getParticularAccount(accountId.toString())
        }

        TestingGetAllAccountTransactions(
            isLoadingTransactions = state.isLoadingTransactions,
            errorGetTransaction = state.errorGetTransaction,
            accountTransactions = state.accountTransactions
        ) {
            viewModel.getAllAccountTransactions(accountId.toString())
        }

        TestingCreateAccount(
            isCreatingAccount = state.isCreatingAccount,
            errorCreatedAccount = state.errorCreatedAccount,
            createdAccount = state.createdAccount,
            onCreateAccount = {
                viewModel.createAccount(
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
            viewModel.deleteAccount(accountId.toString())
        }

        TestingGetAccountBalance(
            isLoadingBalance = state.isLoadingBalance,
            errorGetBalance = state.errorGetBalance,
            balance = state.balance
        ) {
            viewModel.getAccountBalance(accountId.toString())
        }
        Text(
            text = "CUSTOMER ACCOUNT END",
            fontSize = 20.sp,
            modifier = Modifier.padding(16.dp)
        )
    }
}

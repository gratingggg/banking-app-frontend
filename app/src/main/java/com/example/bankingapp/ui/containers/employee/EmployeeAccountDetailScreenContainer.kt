package com.example.bankingapp.ui.containers.employee

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.bankingapp.navigation.AppDestinations
import com.example.bankingapp.navigation.navigateAndClear
import com.example.bankingapp.network.RetrofitInstance
import com.example.bankingapp.repository.account.AccountRepositoryImpl
import com.example.bankingapp.ui.screens.AccountDetailScreen
import com.example.bankingapp.viewmodel.EmployeeAccountViewModel
import com.example.bankingapp.viewmodel.factory.EmployeeAccountViewModelFactory

@Composable
fun EmployeeAccountDetailScreenContainer(
    navController: NavHostController,
    entry: NavBackStackEntry,
    accountId: String,
    modifier: Modifier = Modifier
) {
    val employeeAccountViewModel: EmployeeAccountViewModel = viewModel(
        viewModelStoreOwner = entry,
        factory = EmployeeAccountViewModelFactory(
            employeeAccountRepository = AccountRepositoryImpl(RetrofitInstance.accountApiService)
        )
    )

    val employeeAccountState by employeeAccountViewModel.uiState.collectAsState()
    val employeeTransactions = employeeAccountViewModel.transactions.collectAsLazyPagingItems()

    val snackbar = remember {
        SnackbarHostState()
    }

    Box(
        modifier = modifier
    ) {
        val accountDetails = employeeAccountState.selectedAccount

        if (accountDetails != null) {
            AccountDetailScreen(
                accountId = accountDetails.accountId.toString(),
                accountStatus = accountDetails.accountStatus.toString(),
                accountType = accountDetails.accountType.toString(),
                dateOfIssuance = accountDetails.dateOfIssuance.toString(),
                customerName = accountDetails.customerName.toString(),
                transactions = employeeTransactions,
                onViewAccountTransactions = {
                    navController.navigateAndClear(
                        route = AppDestinations.AccountTransactionsScreen.accountTransactionsRoute(
                            accountId
                        )
                    )
                }
            ) {
                navController.navigateAndClear(
                    route = AppDestinations.ParticularTransactionScreen.particularTransactionRoute(
                        it.toString()
                    )
                )
            }
        }

        SnackbarHost(
            hostState = snackbar,
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomCenter)
        )
    }

    LaunchedEffect(employeeAccountState, employeeTransactions.loadState) {
        val particularError = employeeAccountState.errorSelectedAccount
        val transactionError = employeeTransactions.loadState.refresh as? LoadState.Error
            ?: employeeTransactions.loadState.append as? LoadState.Error

        particularError?.let {
            snackbar.showSnackbar(
                message = it.message,
                withDismissAction = true,
                duration = SnackbarDuration.Short
            )
        }

        transactionError?.error?.message?.let {
            snackbar.showSnackbar(
                message = it,
                withDismissAction = true,
                duration = SnackbarDuration.Short
            )
        }
    }

    LaunchedEffect(Unit) {
        employeeAccountViewModel.getParticularAccount(accountId)
        employeeAccountViewModel.getAllAccountTransactions(accountId)
    }
}
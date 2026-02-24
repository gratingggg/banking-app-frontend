package com.example.bankingapp.ui.containers.employee

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.bankingapp.ui.screens.ViewAllTransactionScreen
import com.example.bankingapp.utils.TransactionStatus
import com.example.bankingapp.utils.TransactionType
import com.example.bankingapp.viewmodel.EmployeeAccountViewModel
import com.example.bankingapp.viewmodel.factory.EmployeeAccountViewModelFactory

@Composable
fun EmployeeAccountTransactionsScreenContainer(
    navController: NavHostController,
    entry: NavBackStackEntry,
    accountId: String,
    modifier: Modifier = Modifier
) {
    val viewModel: EmployeeAccountViewModel = viewModel(
        viewModelStoreOwner = entry,
        factory = EmployeeAccountViewModelFactory(
            employeeAccountRepository = AccountRepositoryImpl(RetrofitInstance.accountApiService)
        )
    )

    val transactions = viewModel.transactions.collectAsLazyPagingItems()

    val snackbar = remember {
        SnackbarHostState()
    }

    Box(
        modifier = modifier
    ) {
        ViewAllTransactionScreen(
            status = TransactionStatus.entries.map {
                it.name.lowercase().replaceFirstChar { it.uppercase() }
            },
            type = TransactionType.entries.map {
                it.name.lowercase().replaceFirstChar { it.uppercase() }
            },
            transactions = transactions,
            onTransactionClick = {
                navController.navigateAndClear(
                    route = AppDestinations.ParticularTransactionScreen.particularTransactionRoute(
                        it.toString()
                    )
                )
            }
        ) {
            viewModel.getAllAccountTransactions(
                accountId = accountId,
                transactionStatusStr = it["Status"],
                transactionTypeStr = it["Type"],
                fromDateStr = it["Date"]
            )
        }

        SnackbarHost(
            hostState = snackbar,
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomCenter)
        )
    }

    LaunchedEffect(transactions.loadState) {
        val error = transactions.loadState.refresh as? LoadState.Error
            ?: transactions.loadState.append as? LoadState.Error

        error?.error?.message?.let {
            snackbar.showSnackbar(
                message = it,
                withDismissAction = true,
                duration = SnackbarDuration.Short
            )
        }
    }

    LaunchedEffect(Unit) {
        viewModel.getAllAccountTransactions(accountId)
    }
}
package com.example.bankingapp.ui.containers.customer

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
import com.example.bankingapp.utils.AccountStatus
import com.example.bankingapp.utils.Constants
import com.example.bankingapp.viewmodel.CustomerAccountViewModel
import com.example.bankingapp.viewmodel.factory.CustomerAccountViewModelFactory
import java.time.format.DateTimeFormatter

@Composable
fun CustomerAccountDetailScreenContainer(
    navController: NavHostController,
    entry: NavBackStackEntry,
    accountId: String,
    modifier: Modifier = Modifier
) {
    val viewModel: CustomerAccountViewModel = viewModel(
        viewModelStoreOwner = entry,
        factory = CustomerAccountViewModelFactory(
            customerAccountRepository = AccountRepositoryImpl(RetrofitInstance.accountApiService)
        )
    )

    val state by viewModel.uiState.collectAsState()
    val transactions = viewModel.transactions.collectAsLazyPagingItems()

    val snackbar = remember {
        SnackbarHostState()
    }

    Box(
        modifier = modifier
    ) {
        var accountDetails = state.selectedAccount

        if(accountDetails != null){
            AccountDetailScreen(
                accountId = accountDetails.accountId.toString(),
                accountStatus = accountDetails.accountStatus.toString(),
                accountType = accountDetails.accountType.toString(),
                deleteAccount = accountDetails.accountStatus == AccountStatus.ACTIVE,
                dateOfIssuance = accountDetails.dateOfIssuance.format(DateTimeFormatter.ofPattern(
                    Constants.LOCAL_DATE_PATTERN)),
                customerName = accountDetails.customerName.toString(),
                transactions = transactions,
                onViewAccountTransactions = {
                    navController.navigateAndClear(
                        route = AppDestinations.AccountTransactionsScreen.accountTransactionsRoute(accountId)
                    )
                },
                onDeleteAccount = {
                    viewModel.deleteAccount(it)
                }
            ) {
                navController.navigateAndClear(
                    route = AppDestinations.ParticularTransactionScreen.particularTransactionRoute(it.toString())
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

    LaunchedEffect(state, transactions.loadState) {
        var particularError = state.errorSelectedAccount
        var transactionError = transactions.loadState.refresh as? LoadState.Error
            ?: transactions.loadState.append as? LoadState.Error

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
        viewModel.getParticularAccount(accountId)
        viewModel.getAllAccountTransactions(accountId)
    }
}
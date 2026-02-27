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
import com.example.bankingapp.repository.transaction.TransactionRepositoryImpl
import com.example.bankingapp.ui.screens.customer.CustomerAccountsScreen
import com.example.bankingapp.viewmodel.CustomerAccountViewModel
import com.example.bankingapp.viewmodel.TransactionViewModel
import com.example.bankingapp.viewmodel.factory.CustomerAccountViewModelFactory
import com.example.bankingapp.viewmodel.factory.TransactionViewModelFactory

@Composable
fun CustomerAccountsScreenContainer(
    navController: NavHostController,
    entry: NavBackStackEntry,
    modifier: Modifier = Modifier
){
    val accountViewModel: CustomerAccountViewModel = viewModel(
        viewModelStoreOwner = entry,
        factory = CustomerAccountViewModelFactory(
            AccountRepositoryImpl(
                RetrofitInstance.accountApiService
            )
        )
    )

    val transactionViewModel: TransactionViewModel = viewModel(
        viewModelStoreOwner = entry,
        factory = TransactionViewModelFactory(
            TransactionRepositoryImpl(
                RetrofitInstance.transactionApiService
            )
        )
    )

    val accountState by accountViewModel.uiState.collectAsState()
    val transactions = transactionViewModel.transactions.collectAsLazyPagingItems()

    val snackbar = remember {
        SnackbarHostState()
    }

    Box(
        modifier = modifier
    ){
        CustomerAccountsScreen(
            modifier = modifier,
            accounts = accountState.accountsList.map { Pair<String, String>(it.accountId.toString(), it.accountType.toString()) },
            transactions = transactions,
            onAccountClick = {
                navController.navigateAndClear(
                    route = AppDestinations.ParticularAccountScreen.particularAccountRoute(it)
                )
            },
            onCheckBalance = {
                navController.navigateAndClear(
                    route = AppDestinations.BalanceScreen.viewBalanceRoute(it)
                )
            },
            onParticularTransactionClick = {
                navController.navigateAndClear(
                    route = AppDestinations.ParticularTransactionScreen.particularTransactionRoute(it.toString())
                )
            },
            onViewAllTransactions = {
                navController.navigateAndClear(
                    route = AppDestinations.CustomerAllTransactionScreen.route
                )
            }
        )

        SnackbarHost(
            hostState = snackbar,
            modifier = Modifier
                .padding(
                bottom = 24.dp
            ).align(Alignment.BottomCenter)
        )
    }

    LaunchedEffect(accountState) {
        if(accountState.errorAccountsList != null){
            val error = accountState.errorAccountsList!!
            snackbar.showSnackbar(
                message = error.message,
                withDismissAction = true,
                duration = SnackbarDuration.Short
            )
        }
    }

    LaunchedEffect(accountState, transactions.loadState) {
        val accountError = accountState.errorAccountsList
        val transactionError = transactions.loadState.refresh as? LoadState.Error
            ?: transactions.loadState.append as? LoadState.Error

        accountError?.message?.let {
            snackbar.showSnackbar(
                message = it,
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

    LaunchedEffect(true) {
        accountViewModel.getAllAccounts()
        transactionViewModel.getCustomerAllTransactions()
    }
}
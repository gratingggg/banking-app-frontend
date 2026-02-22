package com.example.bankingapp.ui.containers

import android.util.Log
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.example.bankingapp.SessionManager
import com.example.bankingapp.navigation.AppDestinations
import com.example.bankingapp.navigation.navigateAndClear
import com.example.bankingapp.network.RetrofitInstance
import com.example.bankingapp.repository.account.AccountRepositoryImpl
import com.example.bankingapp.repository.transaction.TransactionRepositoryImpl
import com.example.bankingapp.ui.screens.CustomerAccountsScreen
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
            ),
            sessionManager = SessionManager.getInstance(LocalContext.current)
        )
    )

    val accountState by accountViewModel.uiState.collectAsState()
    val transactionState by transactionViewModel.uiState.collectAsState()

    val snackbar = remember {
        SnackbarHostState()
    }

    Box(
        modifier = modifier
    ){
        CustomerAccountsScreen(
            modifier = modifier,
            accounts = accountState.accountsList.map { Pair<String, String>(it.accountId.toString(), it.accountType.toString()) },
            transactions = transactionState.customerAllTransaction?.content,
            onAccountClick = {
                navController.navigateAndClear(
                    route = AppDestinations.CustomerParticularAccountScreen.customerParticularAccountRoute(it)
                )
            },
            onCheckBalance = {
                navController.navigateAndClear(
                    route = AppDestinations.CustomerViewBalanceScreen.customerViewBalanceRoute(it)
                )
            },
            onParticularTransactionClick = {
                navController.navigateAndClear(
                    route = AppDestinations.ParticularTransactionScreen.particularTransactionRoute(it.toString())
                )
            },
            onViewAllTransactions = {
                navController.navigateAndClear(
                    route = AppDestinations.CustomerViewAllAccountsAllTransactionsScreen.route
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

    LaunchedEffect(transactionState) {
        if(transactionState.errorCustomerAllTransactions != null) {
            val error = transactionState.errorCustomerAllTransactions!!
            snackbar.showSnackbar(
                message = error.message,
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
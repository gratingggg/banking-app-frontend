package com.example.bankingapp.ui.containers

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
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.bankingapp.SessionManager
import com.example.bankingapp.navigation.AppDestinations
import com.example.bankingapp.navigation.navigateAndClear
import com.example.bankingapp.network.RetrofitInstance
import com.example.bankingapp.repository.transaction.TransactionRepositoryImpl
import com.example.bankingapp.ui.screens.ViewAllTransactionScreen
import com.example.bankingapp.utils.Role
import com.example.bankingapp.utils.TransactionStatus
import com.example.bankingapp.utils.TransactionType
import com.example.bankingapp.viewmodel.TransactionViewModel
import com.example.bankingapp.viewmodel.factory.TransactionViewModelFactory

@Composable
fun ViewAllTransactionScreenContainer(
    navController: NavHostController,
    entry: NavBackStackEntry,
    modifier: Modifier = Modifier,
    customerId: String? = null
) {
    val context = LocalContext.current
    val sessionManager = SessionManager.getInstance(context)
    val role by sessionManager.role.collectAsState(initial = null)

    val viewModel: TransactionViewModel = viewModel(
        viewModelStoreOwner = entry,
        factory = TransactionViewModelFactory(
            transactionRepository = TransactionRepositoryImpl(RetrofitInstance.transactionApiService)
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
            when (role) {
                Role.CUSTOMER -> {
                    viewModel.getCustomerAllTransactions(
                        transactionStatusStr = it["Status"],
                        transactionTypeStr = it["Type"],
                        fromDateStr = it["Date"]
                    )
                }

                Role.EMPLOYEE -> {
                    viewModel.getEmployeeAllTransaction(
                        customerId = customerId ?: Long.MAX_VALUE.toString(),
                        transactionStatusStr = it["Status"],
                        transactionTypeStr = it["Type"],
                        fromDateStr = it["Date"]
                    )
                }

                else -> {}
            }
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
        if(error != null){
            error.error.message?.let {
                snackbar.showSnackbar(
                    message = it,
                    withDismissAction = true,
                    duration = SnackbarDuration.Short
                )
            }
        }
    }

    LaunchedEffect(role) {
        when (role) {
            Role.CUSTOMER -> viewModel.getCustomerAllTransactions()
            Role.EMPLOYEE -> if(!customerId.isNullOrBlank() && customerId != "null") viewModel.getEmployeeAllTransaction(customerId)
            null -> Unit
        }
    }
}
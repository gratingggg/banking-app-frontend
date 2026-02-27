package com.example.bankingapp.ui.containers

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.example.bankingapp.repository.transaction.TransactionRepositoryImpl
import com.example.bankingapp.ui.screens.MoneyTransferScreen
import com.example.bankingapp.utils.TransactionType
import com.example.bankingapp.viewmodel.TransactionViewModel
import com.example.bankingapp.viewmodel.factory.TransactionViewModelFactory

@Composable
fun MoneyTransferScreenContainer(
    navController: NavHostController,
    entry: NavBackStackEntry,
    modifier: Modifier = Modifier
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
    val state by viewModel.uiState.collectAsState()

    val snackbar = remember {
        SnackbarHostState()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        MoneyTransferScreen(
            isErrorTriggered = state.errorCustomerTransfer != null || state.errorEmployeeTransfer != null
        ) {
            viewModel.transferFund(
                amountStr = it["Amount"] ?: Int.MAX_VALUE.toString(),
                fromAccountId = it["FromAccountId"] ?: Long.MAX_VALUE.toString(),
                toAccountId = it["ToAccountId"] ?: Long.MAX_VALUE.toString(),
                transactionTypeStr = TransactionType.TRANSFERRED.name,
                role = role
            )
        }

        SnackbarHost(
            hostState = snackbar,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.BottomCenter)
        )
    }

    LaunchedEffect(state) {
        val error = state.errorCustomerTransfer ?: state.errorEmployeeTransfer

        error?.message?.let {
            snackbar.showSnackbar(
                message = it,
                withDismissAction = true,
                duration = SnackbarDuration.Short
            )
        }
    }

    LaunchedEffect(state.customerTransfer, state.employeeTransfer) {
        val transactionId =
            state.customerTransfer?.transactionId ?: state.employeeTransfer?.transactionId

        transactionId?.let {
            viewModel.clearTransferState()
            navController.navigateAndClear(
                route = AppDestinations.ParticularTransactionScreen.particularTransactionRoute(it.toString()),
                popUpToRoute = AppDestinations.MoneyTransferScreen.route
            )
        }
    }
}
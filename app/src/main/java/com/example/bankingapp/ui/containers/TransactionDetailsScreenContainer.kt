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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.example.bankingapp.network.RetrofitInstance
import com.example.bankingapp.repository.transaction.TransactionRepositoryImpl
import com.example.bankingapp.ui.screens.TransactionDetailsScreen
import com.example.bankingapp.viewmodel.TransactionViewModel
import com.example.bankingapp.viewmodel.factory.TransactionViewModelFactory

@Composable
fun TransactionDetailsScreenContainer(
    navController: NavController,
    entry: NavBackStackEntry,
    transactionId: String,
    modifier: Modifier = Modifier
) {
    val viewModel: TransactionViewModel = viewModel(
        viewModelStoreOwner = entry,
        factory = TransactionViewModelFactory(
            TransactionRepositoryImpl(RetrofitInstance.transactionApiService)
        )
    )

    val state by viewModel.uiState.collectAsState()

    val snackbar = remember {
        SnackbarHostState()
    }

    Box(
        modifier = modifier
    ){
        val detail = state.customerTransaction
        if(detail != null){
            TransactionDetailsScreen(
                accountId = detail.accountId.toString(),
                customerName = detail.self.toString(),
                selfName = detail.self.toString(),
                amount = detail.amount.toString(),
                status = detail.transactionStatus,
                dateTime = detail.dateOfTransaction.toString(),
                transactionId = detail.transactionId.toString(),
                isCredit = detail.isCredit == true,
                toAccountId = detail.toAccountId.toString(),
                failureReason = detail.failureReason,
                fromAccountId = detail.fromAccountId.toString()
            )
        }

        SnackbarHost(
            hostState = snackbar,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 24.dp)
        )
    }

    LaunchedEffect(state) {
        val error = state.errorCustomerTransaction
        if(error != null){
            snackbar.showSnackbar(
                message = error.message,
                duration = SnackbarDuration.Short,
                withDismissAction = true
            )
        }
    }

    LaunchedEffect(true) {
        viewModel.getCustomerTransaction(transactionId)
    }
}
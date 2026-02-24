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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import com.example.bankingapp.SessionManager
import com.example.bankingapp.network.RetrofitInstance
import com.example.bankingapp.repository.account.AccountRepositoryImpl
import com.example.bankingapp.ui.screens.AccountBalanceScreen
import com.example.bankingapp.utils.Role
import com.example.bankingapp.viewmodel.CustomerAccountViewModel
import com.example.bankingapp.viewmodel.EmployeeAccountViewModel
import com.example.bankingapp.viewmodel.factory.CustomerAccountViewModelFactory
import com.example.bankingapp.viewmodel.factory.EmployeeAccountViewModelFactory

@Composable
fun CustomerBalanceScreenContainer(
    entry: NavBackStackEntry,
    accountId: String,
    modifier: Modifier = Modifier
){
    val viewModel: CustomerAccountViewModel = viewModel(
        viewModelStoreOwner = entry,
        factory = CustomerAccountViewModelFactory(
            customerAccountRepository = AccountRepositoryImpl(RetrofitInstance.accountApiService)
        )
    )

    val state by viewModel.uiState.collectAsState()

    val snackbar = remember {
        SnackbarHostState()
    }

    Box(
        modifier = modifier
    ) {
        val balanceDetails = state.balance
        if (balanceDetails != null) {
            AccountBalanceScreen(
                accountId = balanceDetails.accountId.toString(),
                accountType = balanceDetails.accountType.toString(),
                balance = balanceDetails.balance.toString()
            )
        }

        SnackbarHost(
            hostState = snackbar,
            modifier = Modifier
                .padding(12.dp)
                .align(Alignment.BottomCenter)
        )
    }

    LaunchedEffect(state) {
        val error = state.errorGetBalance

        error?.let {
            snackbar.showSnackbar(
                message = it.message,
                withDismissAction = true,
                duration = SnackbarDuration.Short
            )
        }
    }

    LaunchedEffect(Unit) {
        viewModel.getAccountBalance(accountId)
    }
}
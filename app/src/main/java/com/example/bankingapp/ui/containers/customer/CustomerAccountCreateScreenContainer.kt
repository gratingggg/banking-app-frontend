package com.example.bankingapp.ui.containers.customer

import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.example.bankingapp.navigation.AppDestinations
import com.example.bankingapp.navigation.navigateAndClear
import com.example.bankingapp.network.RetrofitInstance
import com.example.bankingapp.repository.account.AccountRepositoryImpl
import com.example.bankingapp.ui.screens.CustomerCreateAccountScreen
import com.example.bankingapp.viewmodel.CustomerAccountViewModel
import com.example.bankingapp.viewmodel.factory.CustomerAccountViewModelFactory

@Composable
fun CustomerAccountCreateScreenContainer(
    navController: NavHostController,
    entry: NavBackStackEntry,
    modifier: Modifier = Modifier
){
    val viewModel: CustomerAccountViewModel = viewModel(
        viewModelStoreOwner = entry,
        factory = CustomerAccountViewModelFactory(
            customerAccountRepository = AccountRepositoryImpl(RetrofitInstance.accountApiService)
        )
    )
    val state by viewModel.uiState.collectAsState()

    val snackbar = remember{
        SnackbarHostState()
    }

    Box(
        modifier = modifier
    ){
        CustomerCreateAccountScreen(
            isErrorTriggered = state.errorCreatedAccount != null
        ) {
            viewModel.createAccount(it)
            if(state.errorCreatedAccount == null){
                state.createdAccount?.accountId?.let {
                    navController.navigateAndClear(
                        route = AppDestinations.ParticularAccountScreen.particularAccountRoute(it.toString()),
                        popUpToRoute = AppDestinations.CustomerCreateAccountScreen.route
                    )
                }
            }
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
        state.errorCreatedAccount?.message?.let {
            snackbar.showSnackbar(
                message = it,
                duration = SnackbarDuration.Short,
                withDismissAction = true
            )
        }
    }
}
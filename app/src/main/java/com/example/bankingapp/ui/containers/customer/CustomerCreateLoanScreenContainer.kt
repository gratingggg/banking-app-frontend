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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.example.bankingapp.navigation.AppDestinations
import com.example.bankingapp.navigation.navigateAndClear
import com.example.bankingapp.network.RetrofitInstance
import com.example.bankingapp.repository.loan.LoanRepositoryImpl
import com.example.bankingapp.ui.screens.CreateLoanScreen
import com.example.bankingapp.utils.LoanType
import com.example.bankingapp.viewmodel.CustomerLoanViewModel
import com.example.bankingapp.viewmodel.factory.CustomerLoanViewModelFactory

@Composable
fun CustomerCreateLoanScreenContainer(
    navController: NavHostController,
    entry: NavBackStackEntry,
    modifier: Modifier = Modifier
){
    val viewModel: CustomerLoanViewModel = viewModel(
        viewModelStoreOwner = entry,
        factory = CustomerLoanViewModelFactory(
            customerLoanRepository = LoanRepositoryImpl(RetrofitInstance.loanApiService)
        )
    )
    val state by viewModel.uiState.collectAsState()

    val snackbar = remember {
        SnackbarHostState()
    }

    Box(
        modifier = modifier
    ){
        CreateLoanScreen(
            isErrorTriggered = state.errorCreateLoan != null
        ) {
            viewModel.createLoan(
                accountId = it["AccountId"] ?: Long.MAX_VALUE.toString(),
                principalAmountStr = it["Principal"] ?: "0",
                loanTypeStr = it["Type"]!!,
                tenureInMonths = it["Tenure"] ?: "0"
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
        state.errorCreateLoan?.message?.let {
            snackbar.showSnackbar(
                message = it,
                withDismissAction = true,
                duration = SnackbarDuration.Short
            )
        }

        state.createdLoan?.loanId?.let {
            navController.navigateAndClear(
                route = AppDestinations.ParticularLoanScreen.particularLoanRoute(it.toString()),
                popUpToRoute = AppDestinations.CustomerCreateLoanScreen.route
            )
        }
    }
}
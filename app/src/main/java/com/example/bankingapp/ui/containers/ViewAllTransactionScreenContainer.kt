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
import java.time.LocalDate

@Composable
fun ViewAllTransactionScreenContainer(
    navController: NavHostController,
    entry: NavBackStackEntry,
    modifier: Modifier = Modifier,
    customerId: String? = null
){
    val context = LocalContext.current
    val sessionManager = SessionManager.getInstance(context)
    val role by sessionManager.role.collectAsState(initial = null)

    val viewModel: TransactionViewModel = viewModel(
        viewModelStoreOwner = entry,
        factory = TransactionViewModelFactory(
            transactionRepository = TransactionRepositoryImpl(RetrofitInstance.transactionApiService),
            sessionManager = sessionManager
        )
    )

    val state by viewModel.uiState.collectAsState()

    val snackbar = remember {
        SnackbarHostState()
    }

    Box(
        modifier = modifier
    ){
        val transactions = when(role){
            Role.CUSTOMER -> state.customerAllTransaction
            Role.EMPLOYEE -> state.employeeAllTransaction
            null -> null
        }
        if(transactions != null){
            ViewAllTransactionScreen(
                status = TransactionStatus.entries.map {
                    it.name.lowercase().replaceFirstChar { it.uppercase() }
                },
                type = TransactionType.entries.map {
                    it.name.lowercase().replaceFirstChar { it.uppercase() }
                },
                transactions = transactions.content,
                onTransactionClick = {
                    navController.navigateAndClear(
                        route = AppDestinations.ParticularTransactionScreen.particularTransactionRoute(it.toString())
                    )
                }
            ) {
                val monthsToMinus: Long = when(it["Date"]){
                    "This month" -> 1
                    "Last 90 days" -> 3
                    "Last 180 days" -> 6
                    else -> 0
                }
                when(role){
                    Role.CUSTOMER -> {
                        viewModel.getCustomerAllTransactions(
                            transactionStatusStr = it["Status"],
                            transactionTypeStr = it["Type"],
                            fromDate = LocalDate.now().minusMonths(monthsToMinus)
                        )
                    }

                    Role.EMPLOYEE -> {
                        viewModel.getEmployeeAllTransaction(
                            customerId = customerId ?: Long.MAX_VALUE.toString(),
                            transactionStatusStr = it["Status"],
                            transactionTypeStr = it["Type"],
                            fromDate = LocalDate.now().minusMonths(monthsToMinus)
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
    }

    LaunchedEffect(state, role) {
        val error = when(role){
            Role.CUSTOMER -> state.errorCustomerAllTransactions
            Role.EMPLOYEE -> state.errorEmployeeAllTransactions
            null -> null
        }
        error?.let {
            snackbar.showSnackbar(
                message = error.message,
                duration = SnackbarDuration.Short,
                withDismissAction = true
            )
        }
    }

    LaunchedEffect(role) {
        when(role){
            Role.CUSTOMER -> viewModel.getCustomerAllTransactions()
            Role.EMPLOYEE -> viewModel.getEmployeeAllTransaction(customerId = customerId ?: Long.MAX_VALUE.toString())
            null -> null
        }
    }
}
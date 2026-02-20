package com.example.bankingapp.ui.containers

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.bankingapp.navigation.AppDestinations
import com.example.bankingapp.navigation.navigateAndClear
import com.example.bankingapp.ui.components.BottomNavItem
import com.example.bankingapp.ui.components.BottomNavigationBar
import com.example.bankingapp.ui.screens.CustomerDashboardScreen

@Composable
fun CustomerDashboardContainer(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    CustomerDashboardScreen(
        modifier = modifier
            .fillMaxSize(),
        onBankTransfer = {
            navController.navigateAndClear(
                route = AppDestinations.CustomerMoneyTransferScreen.route
            )
        },
        onViewAllLoans = {
            navController.navigateAndClear(
                route = AppDestinations.CustomerViewAllLoansScreen.route
            )
        },
        onCreateLoan = {
            navController.navigateAndClear(
                route = AppDestinations.CustomerCreateLoanScreen.route
            )
        },
        onCreateAccount = {
            navController.navigateAndClear(
                route = AppDestinations.CustomerCreateAccountScreen.route
            )
        },
        onCustomerAllTransaction = {
            navController.navigateAndClear(
                route = AppDestinations.CustomerViewAllAccountsAllTransactionsScreen.route
            )
        },
        onCustomerAllNotification = {
            navController.navigateAndClear(
                route = AppDestinations.CustomerViewAllNotificationsScreen.route
            )
        },
        onViewBalance = {
            navController.navigateAndClear(
                route = AppDestinations.CustomerAccountsScreen.route
            )
        }
    )
}
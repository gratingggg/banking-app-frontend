package com.example.bankingapp.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import okhttp3.Route

sealed class AppDestinations(val route: String){

    object Splash: AppDestinations("splash_screen")

    object Login: AppDestinations("login_screen")

    object CustomerDashboard: AppDestinations("customer_dashboard_screen")

    object Welcome: AppDestinations("welcome_screen")

    object Register: AppDestinations("register_screen")

    object EmployeeDashboard: AppDestinations("employee_screen")

    object CustomerCreateAccountScreen: AppDestinations("customer_create_account_screen")

    object CustomerMoneyTransferScreen: AppDestinations("customer_money_transfer_screen")

    object CustomerViewAllLoansScreen: AppDestinations("customer_view_all_loans_screen")

    object CustomerCreateLoanScreen: AppDestinations("customer_create_loan_screen")

    object CustomerViewAllAccountsAllTransactionsScreen: AppDestinations("customer_view_all_accounts_all_transactions_screen")

    object CustomerViewAllNotificationsScreen: AppDestinations("customer_view_all_notifications_screen")

    object CustomerAccountsScreen: AppDestinations("customer_view_accounts")

    object CustomerProfileScreen: AppDestinations("customer_profile_screen")

    object CustomerParticularAccountScreen: AppDestinations("customer_particular_account_screen/{accountId}")

    object CustomerParticularTransactionScreen: AppDestinations("customer_particular_transaction_screen/{transactionId}")

    object CustomerViewBalanceScreen: AppDestinations("customer_view_balance_screen/{accountId}")

    fun customerParticularAccountRoute(accountId: String) = "customer_particular_account_screen/$accountId"

    fun customerParticularTransactionRoute(transactionId: String) = "customer_particular_transaction_screen/$transactionId"

    fun customerViewBalanceRoute(accountId: String) = "customer_view_balance_screen/$accountId"
}
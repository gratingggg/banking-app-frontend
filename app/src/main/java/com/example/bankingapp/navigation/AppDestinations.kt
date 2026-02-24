package com.example.bankingapp.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import okhttp3.Route

sealed class AppDestinations(val route: String){
    object Splash: AppDestinations("splash_screen")
    object Welcome: AppDestinations("welcome_screen")




    object Login: AppDestinations("login_screen")




    object CustomerDashboard: AppDestinations("customer_dashboard_screen")
    object CustomerAccountsScreen: AppDestinations("customer_view_accounts")
    object CustomerProfileScreen: AppDestinations("customer_profile_screen")

    object Register: AppDestinations("register_screen")

    object CustomerCreateAccountScreen: AppDestinations("customer_create_account_screen")
    object CustomerMoneyTransferScreen: AppDestinations("customer_money_transfer_screen")
    object CustomerViewAllLoansScreen: AppDestinations("customer_view_all_loans_screen")
    object CustomerCreateLoanScreen: AppDestinations("customer_create_loan_screen")
    object ViewAllNotificationsScreen: AppDestinations("customer_view_all_notifications_screen")
    object CustomerAllTransactionScreen: AppDestinations("customer_all_transactions_screen")




    object EmployeeDashboard: AppDestinations("employee_screen")
    object EmployeeCustomerAllTransactionsScreen: AppDestinations("employee_customer_all_transactions_screen/{customerId}")



    object ParticularAccountScreen: AppDestinations("particular_account_screen/{accountId}")
    object ParticularTransactionScreen: AppDestinations("particular_transaction_screen/{transactionId}")
    object BalanceScreen: AppDestinations("balance_screen/{accountId}")
    object AccountTransactionsScreen: AppDestinations("account_transactions_screen/{accountId}")


    fun particularAccountRoute(accountId: String) = "particular_account_screen/$accountId"

    fun particularTransactionRoute(transactionId: String) = "particular_transaction_screen/$transactionId"

    fun viewBalanceRoute(accountId: String) = "balance_screen/$accountId"

    fun accountTransactionsRoute(accountId: String) = "account_transactions_screen/$accountId"




    fun employeeCustomerAllTransactionsRoute(customerId: String) = "employee_customer_all_transactions_screen/$customerId"
}
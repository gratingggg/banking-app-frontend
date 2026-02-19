package com.example.bankingapp.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.bankingapp.utils.Routes
import okhttp3.Route

sealed class AppDestinations(val route: String){

    object Splash: AppDestinations(Routes.SPLASH_SCREEN)

    object Login: AppDestinations(Routes.LOGIN_SCREEN)

    object CustomerDashboard: AppDestinations(Routes.CUSTOMER_DASHBOARD_SCREEN)

    object Welcome: AppDestinations(Routes.WELCOME_SCREEN)

    object Register: AppDestinations(Routes.REGISTER_SCREEN)

    object EmployeeDashboard: AppDestinations(Routes.EMPLOYEE_DASHBOARD_SCREEN)
}
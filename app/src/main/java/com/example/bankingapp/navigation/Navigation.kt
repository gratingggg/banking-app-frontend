package com.example.bankingapp.navigation

import android.R.attr.bottom
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.bankingapp.R
import com.example.bankingapp.ui.components.BottomNavItem
import com.example.bankingapp.ui.components.BottomNavigationBar
import com.example.bankingapp.ui.containers.CustomerDashboardContainer
import com.example.bankingapp.ui.containers.LoginScreenContainer
import com.example.bankingapp.ui.containers.RegisterScreenContainer
import com.example.bankingapp.ui.screens.CustomerAccountsScreen
import com.example.bankingapp.ui.screens.CustomerAllNotifications
import com.example.bankingapp.ui.screens.CustomerCreateAccountScreen
import com.example.bankingapp.ui.screens.CustomerCreateLoanScreen
import com.example.bankingapp.ui.screens.CustomerMoneyTransferScreen
import com.example.bankingapp.ui.screens.CustomerProfileScreen
import com.example.bankingapp.ui.screens.CustomerViewAllLoansScreen
import com.example.bankingapp.ui.screens.CustomerViewAllTransactionScreen
import com.example.bankingapp.ui.screens.EmployeeDashboard
import com.example.bankingapp.ui.screens.SplashScreen
import com.example.bankingapp.ui.screens.WelcomeScreen
import com.example.bankingapp.ui.theme.primaryContainerLight
import com.example.bankingapp.ui.theme.veryLightCoralPink

@Composable
fun Navigation() {
    var navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    var currentRoute = navBackStackEntry?.destination?.route

    val navItems: List<BottomNavItem> = listOf(
        BottomNavItem(
            name = "Home",
            imageVector = Icons.Default.Home,
            route = AppDestinations.CustomerDashboard.route
        ),

        BottomNavItem(
            name = "Money",
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_currency_rupee_circle),
            route = AppDestinations.CustomerAccountsScreen.route
        ),

        BottomNavItem(
            name = "Profile",
            imageVector = Icons.Default.AccountCircle,
            route = AppDestinations.CustomerProfileScreen.route
        )
    )

    val destinations = navItems.map { it.route }

    val showBottomBar = currentRoute != null && currentRoute in destinations

    Scaffold(
        bottomBar = {
            if(showBottomBar){
                BottomNavigationBar(
                    currentRoute = currentRoute.toString(),
                    navItemsList = navItems
                ) {
                    navController.navigateAndClear(
                        route = it
                    )
                }
            }
        },
        containerColor = Color.Unspecified
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = AppDestinations.Splash.route
        ) {
            composable(route = AppDestinations.Welcome.route) {
                WelcomeScreen(
                    modifier = Modifier.fillMaxSize(),
                    greetingDescription = stringResource(R.string.welcome_greeting),
                ) {
                    navController.navigateAndClear(
                        route = AppDestinations.Login.route,
                        popUpToRoute = AppDestinations.Welcome.route
                    )
                }
            }

            composable(route = AppDestinations.Splash.route) {
                Surface(
                    color = primaryContainerLight
                ) {
                    SplashScreen(
                        navController = navController
                    )
                }
            }

            composable(route = AppDestinations.Login.route) { entry ->
                LoginScreenContainer(
                    navController = navController,
                    entry = entry
                )
            }

            composable(route = AppDestinations.CustomerDashboard.route) {
                CustomerDashboardContainer(
                    navController = navController,
                    modifier = Modifier
                    .padding(
                        bottom = innerPadding.calculateBottomPadding()
                    )
                )
            }

            composable(route = AppDestinations.Register.route) {
                Surface(
                    color = veryLightCoralPink
                ) {
                    RegisterScreenContainer(
                        navController = navController,
                        entry = it
                    )
                }
            }

            composable(route = AppDestinations.EmployeeDashboard.route) {
                EmployeeDashboard()
            }

            composable(route = AppDestinations.CustomerAccountsScreen.route) {
                CustomerAccountsScreen(
                    modifier = Modifier
                        .padding(
                            bottom = innerPadding.calculateBottomPadding()
                        )
                )
            }

            composable(route = AppDestinations.CustomerProfileScreen.route) {
                CustomerProfileScreen(
                    modifier = Modifier
                        .padding(innerPadding)
                )
            }

            composable(route = AppDestinations.CustomerCreateAccountScreen.route){
                CustomerCreateAccountScreen()
            }

            composable(route = AppDestinations.CustomerMoneyTransferScreen.route) {
                CustomerMoneyTransferScreen()
            }

            composable(route = AppDestinations.CustomerViewAllLoansScreen.route) {
                CustomerViewAllLoansScreen()
            }

            composable(route = AppDestinations.CustomerCreateLoanScreen.route) {
                CustomerCreateLoanScreen()
            }

            composable(route = AppDestinations.CustomerViewAllAccountsAllTransactionsScreen.route) {
                CustomerViewAllTransactionScreen()
            }

            composable(route = AppDestinations.CustomerViewAllNotificationsScreen.route) {
                CustomerAllNotifications()
            }
        }
    }
}

fun NavHostController.navigateAndClear(route: String, popUpToRoute: String? = null) {
    navigate(route) {
        if (popUpToRoute != null) {
            popUpTo(popUpToRoute) {
                inclusive = true
            }
        }
        launchSingleTop = true
    }
}
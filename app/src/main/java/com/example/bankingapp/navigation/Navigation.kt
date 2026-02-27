package com.example.bankingapp.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.bankingapp.R
import com.example.bankingapp.SessionManager
import com.example.bankingapp.ui.components.BottomNavItem
import com.example.bankingapp.ui.components.BottomNavigationBar
import com.example.bankingapp.ui.containers.LoginScreenContainer
import com.example.bankingapp.ui.containers.MoneyTransferScreenContainer
import com.example.bankingapp.ui.containers.TransactionDetailsScreenContainer
import com.example.bankingapp.ui.containers.ViewAllTransactionScreenContainer
import com.example.bankingapp.ui.containers.customer.CustomerAccountCreateScreenContainer
import com.example.bankingapp.ui.containers.customer.CustomerAccountDetailScreenContainer
import com.example.bankingapp.ui.containers.customer.CustomerAccountTransactionsScreenContainer
import com.example.bankingapp.ui.containers.customer.CustomerAccountsScreenContainer
import com.example.bankingapp.ui.containers.customer.CustomerAllNotificationsScreenContainer
import com.example.bankingapp.ui.containers.customer.CustomerBalanceScreenContainer
import com.example.bankingapp.ui.containers.customer.CustomerDashboardContainer
import com.example.bankingapp.ui.containers.customer.ParticularNotificationScreenContainer
import com.example.bankingapp.ui.containers.customer.RegisterScreenContainer
import com.example.bankingapp.ui.containers.employee.EmployeeAccountDetailScreenContainer
import com.example.bankingapp.ui.containers.employee.EmployeeAccountTransactionsScreenContainer
import com.example.bankingapp.ui.containers.employee.EmployeeBalanceScreenContainer
import com.example.bankingapp.ui.screens.employee.EmployeeDashboard
import com.example.bankingapp.ui.screens.SplashScreen
import com.example.bankingapp.ui.screens.WelcomeScreen
import com.example.bankingapp.ui.theme.primaryContainerLight
import com.example.bankingapp.ui.theme.veryLightCoralPink
import com.example.bankingapp.utils.Role

@Composable
fun Navigation() {
    val navController = rememberNavController()
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

    val context = LocalContext.current
    val sessionManager = SessionManager.getInstance(context)
    val token by sessionManager.token.collectAsState(initial = "loading")
    val role by sessionManager.role.collectAsState(initial = null)

    LaunchedEffect(token) {
        if(token == null){
            navController.navigateAndClear(
                route = AppDestinations.Welcome.route,
                popUpToRoute = navController.graph.startDestinationRoute
            )
        }
    }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
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

            composable(route = AppDestinations.EmployeeDashboard.route){
                EmployeeDashboard(
                    modifier = Modifier.fillMaxSize()
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

            composable(route = AppDestinations.CustomerAccountsScreen.route) {
                CustomerAccountsScreenContainer(
                    navController = navController,
                    entry = it,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = innerPadding.calculateBottomPadding())
                )
            }

            composable(
                route = AppDestinations.CustomerAllTransactionScreen.route
            ) {
                Surface(
                    color = veryLightCoralPink
                ) {
                    ViewAllTransactionScreenContainer(
                        navController = navController,
                        entry = it
                    )
                }
            }

            composable(
                route = AppDestinations.EmployeeCustomerAllTransactionsScreen.route,
                arguments = listOf(
                    navArgument("customerId"){
                        type = NavType.StringType
                        nullable = false
                    }
                )
            ) {
                Surface(
                    color = veryLightCoralPink
                ) {
                    val customerId = it.arguments?.getString("customerId")
                    ViewAllTransactionScreenContainer(
                        navController = navController,
                        entry = it,
                        customerId = customerId
                    )
                }
            }

            composable(
                route = AppDestinations.ParticularTransactionScreen.route,
                arguments = listOf(
                    navArgument("transactionId") {
                        type = NavType.StringType
                        nullable = false
                    }
                )
            ) {
                val transactionId = it.arguments?.getString("transactionId") ?: ""
                TransactionDetailsScreenContainer(
                    entry = it,
                    transactionId = transactionId
                )
            }

            composable(
                route = AppDestinations.BalanceScreen.route,
                arguments = listOf(
                    navArgument("accountId") {
                        type = NavType.StringType
                        nullable = false
                    }
                )
            ) {
                val accountId = it.arguments?.getString("accountId") ?: ""
                when(role){
                    Role.CUSTOMER -> CustomerBalanceScreenContainer(
                        entry = it,
                        accountId = accountId
                    )

                    Role.EMPLOYEE -> EmployeeBalanceScreenContainer(
                        entry = it,
                        accountId = accountId
                    )

                    null -> null
                }
            }

            composable(
                route = AppDestinations.ParticularAccountScreen.route,
                arguments = listOf(
                    navArgument("accountId"){
                        type = NavType.StringType
                        nullable = false
                    }
                )
            ) {
                val accountId = it.arguments?.getString("accountId") ?: ""
                Surface(
                    color = veryLightCoralPink
                ) {
                    when(role){
                        Role.CUSTOMER -> CustomerAccountDetailScreenContainer(
                            navController = navController,
                            entry = it,
                            accountId = accountId
                        )

                        Role.EMPLOYEE -> EmployeeAccountDetailScreenContainer(
                            navController = navController,
                            entry = it,
                            accountId = accountId
                        )

                        null -> null
                    }
                }
            }

            composable(
                route = AppDestinations.AccountTransactionsScreen.route,
                arguments = listOf(
                    navArgument("accountId") {
                        type = NavType.StringType
                        nullable = false
                    }
                )
            ) {
                val accountId = it.arguments?.getString("accountId") ?: ""
                Surface(
                    color = veryLightCoralPink
                ) {
                    when(role){
                        Role.CUSTOMER -> CustomerAccountTransactionsScreenContainer(
                            navController = navController,
                            entry = it,
                            accountId = accountId
                        )

                        Role.EMPLOYEE -> EmployeeAccountTransactionsScreenContainer(
                            navController = navController,
                            entry = it,
                            accountId = accountId
                        )

                        null -> null
                    }
                }
            }

            composable(
                route = AppDestinations.CustomerCreateAccountScreen.route
            ) {
                CustomerAccountCreateScreenContainer(
                    navController = navController,
                    entry = it
                )
            }

            composable(
                route = AppDestinations.ViewAllNotificationsScreen.route
            ){
                Surface {
                    CustomerAllNotificationsScreenContainer(
                        navController = navController,
                        entry = it
                    )
                }
            }

            composable(
                route = AppDestinations.ParticularNotificationScreen.route,
                arguments = listOf(
                    navArgument("notificationId") {
                        type = NavType.StringType
                        nullable = false
                    }
                )
            ) {
                val notificationId = it.arguments?.getString("notificationId") ?: Long.MAX_VALUE.toString()
                Surface {
                    ParticularNotificationScreenContainer(
                        entry = it,
                        notificationId = notificationId
                    )
                }
            }

            composable(
                route = AppDestinations.MoneyTransferScreen.route
            ) {
                Surface {
                    MoneyTransferScreenContainer(
                        navController = navController,
                        entry = it
                    )
                }
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
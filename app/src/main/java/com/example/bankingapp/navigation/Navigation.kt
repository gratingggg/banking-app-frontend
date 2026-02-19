package com.example.bankingapp.navigation

import android.util.Log.e
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bankingapp.R
import com.example.bankingapp.ui.containers.LoginScreenContainer
import com.example.bankingapp.ui.containers.RegisterScreenContainer
import com.example.bankingapp.ui.screens.EmployeeDashboard
import com.example.bankingapp.ui.screens.LoginScreen
import com.example.bankingapp.ui.screens.SplashScreen
import com.example.bankingapp.ui.screens.WelcomeScreen
import com.example.bankingapp.ui.screens.customer.CustomerDashboardScreen
import com.example.bankingapp.ui.screens.customer.RegisterScreen
import com.example.bankingapp.ui.theme.lightCoralPink
import com.example.bankingapp.ui.theme.onPrimaryContainerLight
import com.example.bankingapp.ui.theme.onPrimaryLight
import com.example.bankingapp.ui.theme.primaryContainerLight
import com.example.bankingapp.ui.theme.tertiaryContainerDarkHighContrast
import com.example.bankingapp.ui.theme.veryLightCoralPink
import com.example.bankingapp.viewmodel.LoginUiState
import kotlinx.coroutines.launch
import java.util.Map.entry

@Composable
fun Navigation(){
    var navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppDestinations.Splash.route
    ){
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
            CustomerDashboardScreen()
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

        composable( route = AppDestinations.EmployeeDashboard.route ){
            EmployeeDashboard()
        }
    }
}

fun NavHostController.navigateAndClear(route: String, popUpToRoute: String? = null) {
    navigate(route){
        if(popUpToRoute != null){
            popUpTo(popUpToRoute) {
                inclusive = true
            }
        }
        launchSingleTop = true
    }
}

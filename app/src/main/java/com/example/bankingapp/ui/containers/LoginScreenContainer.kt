package com.example.bankingapp.ui.containers

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import com.example.bankingapp.network.RetrofitInstance.sessionManager
import com.example.bankingapp.repository.auth.AuthRepositoryImpl
import com.example.bankingapp.ui.screens.LoginScreen
import com.example.bankingapp.utils.Role
import com.example.bankingapp.viewmodel.AuthViewModel
import com.example.bankingapp.viewmodel.LoginUiState
import com.example.bankingapp.viewmodel.factory.AuthViewModelFactory

@Composable
fun LoginScreenContainer(
    navController: NavHostController,
    entry: NavBackStackEntry
) {
    val context = LocalContext.current
    val sessionManager = remember {
        SessionManager.getInstance(context)
    }

    val viewModel: AuthViewModel = viewModel(
        viewModelStoreOwner = entry,
        factory = AuthViewModelFactory(
            AuthRepositoryImpl(
                apiService = RetrofitInstance.authApiService,
                sessionManager = sessionManager
            )
        )
    )

    val state by viewModel.loginState.collectAsState()
    var isErrorTriggered by rememberSaveable {
        mutableStateOf(false)
    }

    val snackbar = remember {
        SnackbarHostState()
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LoginScreen(
            isErrorTriggered = isErrorTriggered,
            onButtonClicked = { firstField, secondField ->
                viewModel.login(firstField, secondField)
            }
        ) {
            navController.navigateAndClear(
                route = AppDestinations.Register.route
            )
        }

        SnackbarHost(
            hostState = snackbar,
            modifier = Modifier
                .padding(12.dp)
                .align(Alignment.BottomCenter)
        )
    }


    LaunchedEffect(state) {
        isErrorTriggered = state is LoginUiState.Failure
        when (val loginState = state) {
            LoginUiState.Idle -> {}

            LoginUiState.Loading -> {}

            is LoginUiState.Success -> {
                isErrorTriggered = false
                val role = loginState.data.role

                val route = if (role == Role.EMPLOYEE) AppDestinations.EmployeeDashboard.route
                else AppDestinations.CustomerDashboard.route
                navController.navigateAndClear(
                    route = route,
                    popUpToRoute = AppDestinations.Login.route
                )
            }

            is LoginUiState.Failure -> {
                snackbar.showSnackbar(
                    message = loginState.error.message,
                    duration = SnackbarDuration.Short,
                    withDismissAction = true
                )
            }

        }

    }
}

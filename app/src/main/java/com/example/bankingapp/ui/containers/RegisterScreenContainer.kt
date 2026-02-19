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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.example.bankingapp.repository.register.RegisterRepositoryImpl
import com.example.bankingapp.ui.screens.customer.RegisterScreen
import com.example.bankingapp.viewmodel.RegisterState
import com.example.bankingapp.viewmodel.RegisterViewModel
import com.example.bankingapp.viewmodel.factory.RegisterViewModelFactory

@Composable
fun RegisterScreenContainer(
    navController: NavHostController,
    entry: NavBackStackEntry,
    modifier: Modifier = Modifier
){
    val viewModel: RegisterViewModel = viewModel(
        viewModelStoreOwner = entry, 
        factory = RegisterViewModelFactory(RegisterRepositoryImpl(RetrofitInstance.registerApiService))
    )
    
    val registerState by viewModel.registerState.collectAsState()
    var isErrorTriggered by rememberSaveable {
        mutableStateOf(false)
    }
    val snackbar = remember{
        SnackbarHostState()
    }

    Box(
        modifier = modifier
    ){
        RegisterScreen(
            modifier = Modifier.padding(24.dp),
            isErrorTriggered = isErrorTriggered,
            onButtonClicked = {
                viewModel.register(
                    name = it["name"].toString(),
                    email = it["email"].toString(),
                    phoneNumber = it["phoneNumber"].toString(),
                    dateOfBirthStr = it["dateOfBirthStr"].toString(),
                    address = it["address"].toString(),
                    genderStr = it["genderStr"].toString(),
                    username = it["username"].toString(),
                    password = it["password"].toString(),
                    aadharNo = it["aadharNumber"].toString(),
                )
            }){
            navController.navigateAndClear(
                route = AppDestinations.Login.route,
                popUpToRoute = AppDestinations.Register.route
            )
        }

        SnackbarHost(
            hostState = snackbar,
            modifier = Modifier
                .padding(24.dp)
                .align(Alignment.BottomCenter)
        )
    }

    LaunchedEffect(registerState) {
        isErrorTriggered = registerState is RegisterState.Failure

        when(val state = registerState){
            RegisterState.Idle -> {}
            RegisterState.Loading -> {}
            is RegisterState.Success -> {
                navController.navigateAndClear(
                    route = AppDestinations.Login.route,
                    popUpToRoute = AppDestinations.Register.route
                )
            }
            is RegisterState.Failure -> {
                snackbar.showSnackbar(
                    message = state.error.message,
                    withDismissAction = true,
                    duration = SnackbarDuration.Short
                )
            }
        }
    }
}
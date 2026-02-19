package com.example.bankingapp.testing

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bankingapp.viewmodel.AuthViewModel
import com.example.bankingapp.viewmodel.LoginUiState


@Composable
fun TestingAuthApi(viewModel: AuthViewModel, username: String, password: String = "rudra"){
    val state by viewModel.loginState.collectAsState()

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                viewModel.login(username, password)
            }) {
            Text(text = "Login",
                fontSize = 20.sp,
                modifier = Modifier.padding(horizontal = 24.dp))
        }

        when(val loginState = state){
            LoginUiState.Idle -> {}
            LoginUiState.Loading -> Text("Login is in process..............")
            is LoginUiState.Success -> Text("Login successfull")
            is LoginUiState.Failure -> Text("Error: ${loginState.error.message}\nStatus code: ${loginState.error.statusCode}")
        }
    }
}

package com.example.bankingapp.testing

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bankingapp.viewmodel.AuthViewModel
import com.example.bankingapp.viewmodel.LoginState


@Composable
fun TestingAuthApi(viewModel: AuthViewModel, username: String){
    val state by viewModel.loginState.collectAsState()

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                viewModel.login(username, "rudra")
            }) {
            Text(text = "Login",
                fontSize = 20.sp,
                modifier = Modifier.padding(horizontal = 24.dp))
        }

        when(val loginState = state){
            LoginState.Idle -> {}
            LoginState.Loading -> Text("Login is in process..............")
            is LoginState.Success -> Text("Login successfull")
            is LoginState.Failure -> Text("Error: ${loginState.error.message}\nStatus code: ${loginState.error.statusCode}")
        }
    }
}

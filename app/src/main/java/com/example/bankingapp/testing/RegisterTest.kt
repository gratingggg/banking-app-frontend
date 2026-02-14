package com.example.bankingapp.testing

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bankingapp.viewmodel.RegisterState
import com.example.bankingapp.viewmodel.RegisterViewModel
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue

@Composable
fun TestingRegisterApi(viewModel: RegisterViewModel){
    val state by viewModel.registerState.collectAsState()

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                viewModel.register(
                    name = "Rudra",
                    email = "rudra@gmail.com",
                    phoneNumber = "1231231234",
                    dateOfBirthStr = "25-02-2007",
                    address = "India",
                    genderStr = "MALE",
                    username = "rudra",
                    password = "rudra",
                    aadharNo = "123123123123"
                )
            })
        {
            Text(text = "Register",
                fontSize = 20.sp,
                modifier = Modifier.padding(horizontal = 24.dp))
        }

        when(val registerState = state){
            RegisterState.Idle -> {}
            RegisterState.Loading -> Text("Registration is in process..............")
            is RegisterState.Success -> Text("Login successfull\n${registerState.data}")
            is RegisterState.Failure -> {
                Text("\t\tError: ${registerState.error.message}\n\t\tStatus code: ${registerState.error.statusCode}")
            }
        }
    }
}
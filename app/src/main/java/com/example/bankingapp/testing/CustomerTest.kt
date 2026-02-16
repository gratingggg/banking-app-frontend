package com.example.bankingapp.testing

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bankingapp.viewmodel.CustomerViewModel

@Composable
fun TestingCustomerApi(viewModel: CustomerViewModel) {
    val state by viewModel.uiState.collectAsState()

    Column {
        Button(
            onClick = {
                viewModel.getMyDetails()
            }
        ) {
            Text(
                text = "Get My Details(Customer)",
                fontSize = 20.sp,
                modifier = Modifier.padding(16.dp)
            )
        }

        Text(
            text = when {
                state.isGettingMyDetails -> "loading my details........"
                state.errorMyDetails != null -> state.errorMyDetails.toString()
                state.myDetails != null -> state.myDetails.toString()
                else -> "else"
            }
        )
    }
}
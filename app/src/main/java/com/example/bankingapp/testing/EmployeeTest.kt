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
import com.example.bankingapp.viewmodel.EmployeeViewModel

@Composable
fun TestingEmployeeApi(viewModel: EmployeeViewModel){
    val state by viewModel.uiState.collectAsState()

    val loanId: Long = 1

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column {
            Button(
                onClick = {
                    viewModel.getMyDetails()
                }
            ) {
                Text(
                    text = "Get My Details(Employee)",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(16.dp)
                )
            }

            Text(
                text = when {
                    state.isGettingMyDetails -> "loading my details............."
                    state.errorMyDetails != null -> state.errorMyDetails.toString()
                    state.myDetails != null  -> state.myDetails.toString()
                    else -> "else"
                }
            )
        }

        Column {
            Button(
                onClick = {
                    viewModel.processLoans(
                        loanId = loanId,
                        action = "APPROVE"
                    )
                }
            ) {
                Text(
                    text = "Process loan",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(16.dp)
                )
            }

            Text(
                text = when {
                    state.isProcessingLoan -> "processing loan............."
                    state.errorProcessLoan != null -> state.errorProcessLoan.toString()
                    state.processedLoan != null  -> state.processedLoan.toString()
                    else -> "else"
                }
            )
        }



        Column {
            Button(
                onClick = {
                    viewModel.disburseLoans(
                        loanId = loanId
                    )
                }
            ) {
                Text(
                    text = "Disburse loan",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(16.dp)
                )
            }

            Text(
                text = when {
                    state.isDisbursingLoan -> "disbursing loan............."
                    state.errorDisburseLoan != null -> state.errorDisburseLoan.toString()
                    state.disbursedLoan != null  -> state.disbursedLoan.toString()
                    else -> "else"
                }
            )
        }
    }
}


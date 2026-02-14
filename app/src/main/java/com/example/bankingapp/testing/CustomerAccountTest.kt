package com.example.bankingapp.testing

import android.util.Log.e
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
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
import com.example.bankingapp.models.account.AccountRequestDto
import com.example.bankingapp.utils.AccountType
import com.example.bankingapp.viewmodel.CustomerAccountViewModel

@Composable
fun TestingCustomerAccountApi(viewModel: CustomerAccountViewModel){
    val state by viewModel.uiState.collectAsState()
    var accountId: Long = 234
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Column {
            Button(
                onClick = {
                    viewModel.getAllAccounts()
                    accountId = state.accountsList.first().accountId
                }
            ) {
                Text(
                    text = "All Accounts",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(16.dp)
                )
            }

            Text(
                text = if(state.error != null) {
                    "Error : " + state.error
                } else {
                    if(state.isLoadingAccounts) "loading accounts.............."
                    else state.accountsList.first().toString()
                }
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        Column {
            Button(
                onClick = {
                    viewModel.getParticularAccount(accountId)
                }
            ) {
                Text(
                    text = "Particular account",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(16.dp)
                )
            }

            Text(
                text = if(state.error != null) {
                    "Error : ${state.error}"
                } else {
                    if(state.isLoadingAccountDetails) "loading account details.............."
                    else state.selectedAccount.toString()
                }
            )
        }

        Column {
            Button(
                onClick = {
                    viewModel.getAllAccountTransactions(accountId)
                }
            ) {
                Text(
                    text = "All transactions",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(16.dp)
                )
            }

            Text(
                text = if(state.error != null) {
                    "Error : ${state.error}"
                } else {
                    if(state.isLoadingTransactions) "loading transactions.............."
                    else {
                        var str = ""
                        state.accountTransactions?.size?.let {
                            if(it >= 1) state.accountTransactions?.content
                                ?.forEach { cur ->
                                    str = str + cur
                                }
                        }
                        str
                    }
                }
            )
        }

        Column {
            Button(
                onClick = {
                    viewModel.createAccount(
                        AccountRequestDto(
                            accountType = AccountType.CURRENT
                        )
                    )
                }
            ) {
                Text(
                    text = "Create Account",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(16.dp)
                )
            }

            Text(
                text = if(state.error != null) {
                    "Error : ${state.error}"
                } else {
                    if(state.isCreatingAccount) "creating account.............."
                    else state.selectedAccount.toString()
                }
            )
        }

        Column {
            Button(
                onClick = {
                    viewModel.deleteAccount(accountId)
                }
            ) {
                Text(
                    text = "Delete account",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(16.dp)
                )
            }

            Text(
                text = if(state.error != null) {
                    "Error : ${state.error}"
                } else {
                    if(state.isLoadingAccountDetails) "deleting account.............."
                    else state.selectedAccount.toString()
                }
            )
        }

        Column {
            Button(
                onClick = {
                    viewModel.getAccountBalance(accountId)
                }
            ) {
                Text(
                    text = "Balance",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(16.dp)
                )
            }

            Text(
                text = if(state.error != null) {
                    "Error : ${state.error}"
                } else {
                    if(state.isLoadingAccountDetails) "loading account balance.............."
                    else state.selectedAccount.toString()
                }
            )
        }
    }
}


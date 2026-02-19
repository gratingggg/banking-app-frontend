package com.example.bankingapp.testing

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bankingapp.models.account.AccountBalanceResponseDto
import com.example.bankingapp.models.account.AccountResponseDto
import com.example.bankingapp.models.account.AccountSummaryDto
import com.example.bankingapp.models.exception.ErrorResponse
import com.example.bankingapp.models.transactions.TransactionPagedResultDto
import com.example.bankingapp.models.transactions.TransactionResponseDto


@Composable
fun TestingGetAllAccounts(
    isLoadingAccounts: Boolean,
    errorAccountsList: ErrorResponse?,
    accountsList: List<AccountSummaryDto> = emptyList(),
    onGetAllAccounts: () -> Unit,
    onAccountExist: (Long) -> Unit
) {
    Column {
        Button(
            onClick = onGetAllAccounts
        ) {
            Text(
                text = "All Accounts",
                fontSize = 20.sp,
                modifier = Modifier.padding(16.dp)
            )
        }

        Text(
            text = when {
                isLoadingAccounts -> "loading accounts............."
                errorAccountsList != null -> "Error : $errorAccountsList"
                accountsList.isEmpty() -> "No accounts"
                else -> {
                    accountsList.toString()
                }
            }
        )
    }

    if(accountsList.isNotEmpty()){
        LaunchedEffect(accountsList) {
            onAccountExist(accountsList.last().accountId)
        }
    }
}

@Composable
fun TestingGetParticularAccount(
    isLoadingSelectedAccountDetails: Boolean,
    errorSelectedAccount: ErrorResponse?,
    selectedAccount: AccountResponseDto?,
    onParticularAccountClick: () -> Unit
) {
    Column {
        Button(
            onClick = {
                onParticularAccountClick()
            }
        ) {
            Text(
                text = "Particular account",
                fontSize = 20.sp,
                modifier = Modifier.padding(16.dp)
            )
        }
        Text(
            text = when {
                isLoadingSelectedAccountDetails -> "loading account details............"
                errorSelectedAccount != null -> "Error : $errorSelectedAccount"
                selectedAccount != null -> selectedAccount.toString()
                else -> "else"
            }
        )
    }
}

@Composable
fun TestingGetAllAccountTransactions(
    isLoadingTransactions: Boolean,
    errorGetTransaction: ErrorResponse?,
    accountTransactions: TransactionPagedResultDto?,
    onGetTransactions: () -> Unit
) {
    Column {
        Button(
            onClick = {
                onGetTransactions()
            }
        ) {
            Text(
                text = "All transactions",
                fontSize = 20.sp,
                modifier = Modifier.padding(16.dp)
            )
        }

        Text(
            text = when {
                isLoadingTransactions -> "loading transactions................"
                errorGetTransaction != null -> "Error : $errorGetTransaction"
                accountTransactions?.content?.isEmpty() == true -> "No account transactions"
                else -> accountTransactions.toString()
            }
        )
    }
}

@Composable
fun TestingCreateAccount(
    isCreatingAccount: Boolean,
    errorCreatedAccount: ErrorResponse?,
    createdAccount: AccountResponseDto?,
    onCreateAccount: () -> Unit,
    onAccountCreated: (Long) -> Unit
) {
    Column {
        Button(
            onClick = onCreateAccount
        ) {
            Text(
                text = "Create Account",
                fontSize = 20.sp,
                modifier = Modifier.padding(16.dp)
            )
        }

        Text(
            text = when {
                isCreatingAccount -> "creating account............."
                errorCreatedAccount != null -> "Error : $errorCreatedAccount"
                createdAccount != null -> createdAccount.toString()
                else -> "else"
            }
        )
    }

    createdAccount?.accountId?.let {
        LaunchedEffect(it) {
            onAccountCreated(it)
        }
    }
}

@Composable
fun TestingAccountDeletion(
    isDeletingAccount: Boolean,
    errorDeletedAccount: ErrorResponse?,
    deletedAccount: AccountResponseDto?,
    onDeleteAccount: () -> Unit
) {
    Column {
        Button(
            onClick = onDeleteAccount
        ) {
            Text(
                text = "Delete account",
                fontSize = 20.sp,
                modifier = Modifier.padding(16.dp)
            )
        }

        Text(
            text = when {
                isDeletingAccount -> "deleting account..........."
                errorDeletedAccount != null -> "Error : $errorDeletedAccount"
                deletedAccount != null -> deletedAccount.toString()
                else -> "else"
            }
        )
    }
}

@Composable
fun TestingGetAccountBalance(
    isLoadingBalance: Boolean,
    errorGetBalance: ErrorResponse?,
    balance: AccountBalanceResponseDto?,
    onGetBalance: () -> Unit
) {
    Column {
        Button(
            onClick = onGetBalance
        ) {
            Text(
                text = "Balance",
                fontSize = 20.sp,
                modifier = Modifier.padding(16.dp)
            )
        }

        Text(
            text = when {
                isLoadingBalance -> "loading balance..........."
                errorGetBalance != null -> "Error : $errorGetBalance"
                balance != null -> balance.toString()
                else -> "else"
            }
        )
    }
}

@Composable
fun TestingDeposit(
    isDepositing: Boolean,
    errorDeposit: ErrorResponse?,
    deposit: TransactionResponseDto?,
    onDeposit: () -> Unit
) {
    Column {
        Button(
            onClick = onDeposit
        ) {
            Text(
                text = "Deposit",
                fontSize = 20.sp,
                modifier = Modifier.padding(16.dp)
            )
        }

        Text(
            text = when {
                isDepositing -> "depositing..........."
                errorDeposit != null -> "Error : $errorDeposit"
                deposit != null -> deposit.toString()
                else -> "else"
            }
        )
    }
}

@Composable
fun TestingWithdraw(
    isWithdrawing: Boolean,
    errorWithdraw: ErrorResponse?,
    withdraw: TransactionResponseDto?,
    onWithdraw: () -> Unit
) {
    Column {
        Button(
            onClick = onWithdraw
        ) {
            Text(
                text = "Withdrawing",
                fontSize = 20.sp,
                modifier = Modifier.padding(16.dp)
            )
        }

        Text(
            text = when {
                isWithdrawing -> "withdrawing..........."
                errorWithdraw != null -> "Error : $errorWithdraw"
                withdraw != null -> withdraw.toString()
                else -> "else"
            }
        )
    }
}
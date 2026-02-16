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
import com.example.bankingapp.models.exception.ErrorResponse
import com.example.bankingapp.models.loan.LoanPagedResultDto
import com.example.bankingapp.models.loan.LoanResponseDto
import com.example.bankingapp.models.transactions.TransactionPagedResultDto
import com.example.bankingapp.models.transactions.TransactionResponseDto

@Composable
fun TestingGetAllLoans(
    isLoadingLoans: Boolean,
    errorAllLoans: ErrorResponse?,
    allLoans: LoanPagedResultDto?,
    btnText: String = "All Loans",
    onGetAllLoans: () -> Unit,
    onLoansExist: (Long) -> Unit,
    onAccountExist: (Long) -> Unit
) {
    Column {
        Button(
            onClick = onGetAllLoans
        ) {
            Text(
                text = btnText,
                fontSize = 20.sp,
                modifier = Modifier.padding(16.dp)
            )
        }

        Text(
            text = when {
                isLoadingLoans -> "loading loans............."
                errorAllLoans != null -> "Error : $errorAllLoans"
                allLoans == null -> "Null loans"
                allLoans.content.isEmpty() -> "no loans"
                else -> {
                    allLoans.toString()
                }
            }
        )
    }

    if (allLoans != null && allLoans.content.isNotEmpty()) {
        LaunchedEffect(allLoans) {
            onLoansExist(allLoans.content.last().loanId)
            onAccountExist(allLoans.content.last().accountId)
        }
    }
}

@Composable
fun TestingGetParticularLoan(
    isLoadingLoanDetails: Boolean,
    errorSelectedLoan: ErrorResponse?,
    selectedLoan: LoanResponseDto?,
    onLoanSelected: () -> Unit
) {
    Column {
        Button(
            onClick = onLoanSelected
        ) {
            Text(
                text = "Particular Loan",
                fontSize = 20.sp,
                modifier = Modifier.padding(16.dp)
            )
        }

        Text(
            text = when {
                isLoadingLoanDetails -> "loading loan details.........."
                errorSelectedLoan != null -> "$errorSelectedLoan"
                selectedLoan != null -> selectedLoan.toString()
                else -> "else"
            }
        )
    }
}

@Composable
fun TestingCreateLoan(
    isCreatingLoan: Boolean,
    errorCreateLoan: ErrorResponse?,
    createdLoan: LoanResponseDto?,
    onCreateLoan: () -> Unit,
    onLoanCreated: (Long) -> Unit
) {
    Column {
        Button(
            onClick = onCreateLoan
        ) {
            Text(
                text = "Create Loans",
                fontSize = 20.sp,
                modifier = Modifier.padding(16.dp)
            )
        }

        Text(
            text = when {
                isCreatingLoan -> "loan creating........"
                errorCreateLoan != null -> "$errorCreateLoan"
                createdLoan != null -> "$createdLoan"
                else -> "else"
            }
        )
    }

    createdLoan?.loanId?.let {
        LaunchedEffect(it) {
            onLoanCreated(it)
        }
    }
}

@Composable
fun TestingRepayLoan(
    isRepayingLoan: Boolean,
    errorRepayLoan: ErrorResponse?,
    repaidLoan: TransactionResponseDto?,
    onRepayLoan: () -> Unit
) {
    Column {
        Button(
            onClick = onRepayLoan
        ) {
            Text(
                text = "Repay Loan",
                fontSize = 20.sp,
                modifier = Modifier.padding(16.dp)
            )
        }

        Text(
            text = when {
                isRepayingLoan -> "repaying loans............."
                errorRepayLoan != null -> "Error : $errorRepayLoan"
                repaidLoan != null  -> repaidLoan.toString()
                else -> "else"
            }
        )
    }
}

@Composable
fun TestingGetLoanTransactions(
    isLoadingTransactions: Boolean,
    errorTransaction: ErrorResponse?,
    transactions: TransactionPagedResultDto?,
    onGetTransactions: () -> Unit
) {
    Column {
        Button(
            onClick = onGetTransactions
        ) {
            Text(
                text = "Transactions",
                fontSize = 20.sp,
                modifier = Modifier.padding(16.dp)
            )
        }

        Text(
            text = when {
                isLoadingTransactions -> "loading transactions......"
                errorTransaction != null -> errorTransaction.toString()
                transactions == null -> "Null transactions"
                transactions.content.isEmpty() -> "No transactions"
                else -> transactions.toString()
            }
        )
    }
}

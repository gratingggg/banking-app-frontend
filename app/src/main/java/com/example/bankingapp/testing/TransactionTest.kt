package com.example.bankingapp.testing

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bankingapp.models.exception.ErrorResponse
import com.example.bankingapp.models.transactions.TransactionPagedResultDto
import com.example.bankingapp.models.transactions.TransactionResponseDto
import com.example.bankingapp.viewmodel.TransactionViewModel
import java.nio.file.WatchEvent

@Composable
fun TestingTransactionApi(viewModel: TransactionViewModel){
    val state by viewModel.uiState.collectAsState()

    var transactionId by remember {
        mutableLongStateOf(345)
    }
    
    val customerId: Long = 1

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(24.dp)
    ) {
        GetAllTransactions(
            isLoadingAllTransactions = state.isLoadingCustomerAllTransactions,
            errorTransactions = state.errorCustomerAllTransactions,
            transactions = state.customerAllTransaction,
            onGetAllTransactions = { 
                viewModel.getCustomerAllTransactions()
            }
        ) { 
            transactionId = it
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        GetTransaction(
            isLoadingTransaction = state.isLoadingCustomerTransaction,
            errorTransaction = state.errorCustomerTransaction,
            transaction = state.customerTransaction
        ) {
            viewModel.getCustomerTransaction(transactionId.toString())
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        TransferFund(
            isTransferring = state.isTransferringCustomer,
            errorTransfer = state.errorCustomerTransfer,
            transferred = state.customerTransfer
        ) {
            viewModel.transferFundByCustomer(
                amountStr = "1500",
                fromAccountId = "1",
                toAccountId = "2",
                transactionTypeStr = "TRANSFERRED"
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        GetAllTransactions(
            btnText = "Get All Transactions(Employee)",
            isLoadingAllTransactions = state.isLoadingEmployeeAllTransactions,
            errorTransactions = state.errorEmployeeAllTransactions,
            transactions = state.employeeAllTransaction,
            onGetAllTransactions = {
                viewModel.getEmployeeAllTransaction(customerId.toString())
            }
        ) {
            transactionId = it
        }

        Spacer(modifier = Modifier.height(16.dp))

        GetTransaction(
            btnText = "Get Transaction(Employee)",
            isLoadingTransaction = state.isLoadingEmployeeTransaction,
            errorTransaction = state.errorEmployeeTransaction,
            transaction = state.employeeTransaction
        ) {
            viewModel.getEmployeeTransaction(transactionId.toString())
        }

        Spacer(modifier = Modifier.height(16.dp))

        TransferFund(
            btnText = "Transfer fund(Employee)",
            isTransferring = state.isTransferringEmployee,
            errorTransfer = state.errorEmployeeTransfer,
            transferred = state.employeeTransfer
        ) {
            viewModel.transferFundByEmployee(
                amountStr = "1000",
                fromAccountId = "2",
                toAccountId = "1",
                transactionTypeStr = "TRANSFERRED"
            )
        }
    }
}

@Composable
fun GetTransaction(
    btnText: String = "Get Customer Transaction", 
    isLoadingTransaction: Boolean, 
    errorTransaction: ErrorResponse?, 
    transaction: TransactionResponseDto?, 
    onGetTransaction: () -> Unit
){
    Column {
        Button(
            onClick = onGetTransaction
        ) {
            Text(
                text = btnText,
                fontSize = 20.sp,
                modifier = Modifier.padding(16.dp)
            )
        }

        Text(
            text = when {
                isLoadingTransaction -> "loading transaction..........."
                errorTransaction != null -> errorTransaction.toString()
                transaction != null -> transaction.toString()
                else -> "else"
            }
        )
    }
}

@Composable
fun GetAllTransactions(
    btnText: String = "Get All Customer Transactions", 
    isLoadingAllTransactions: Boolean, 
    errorTransactions: ErrorResponse?, 
    transactions: TransactionPagedResultDto?, 
    onGetAllTransactions: () -> Unit, 
    onAllTransactions: (Long) -> Unit
){
    Column {
        Button(
            onClick = onGetAllTransactions
        ) {
            Text(
                text = btnText,
                fontSize = 20.sp,
                modifier = Modifier.padding(16.dp)
            )
        }

        Text(
            text = when {
                isLoadingAllTransactions -> "loading all transactions..........."
                errorTransactions != null -> errorTransactions.toString()
                transactions == null -> "null"
                transactions.content.isEmpty() -> "no transactions"
                else -> transactions.toString()
            }
        )
    }
    
    transactions?.content?.let {
        LaunchedEffect(it) {
            onAllTransactions(it.last().transactionId)
        }
    }
}

@Composable
fun TransferFund(
    btnText: String = "Transfer Fund(Customer)", 
    isTransferring: Boolean, 
    errorTransfer: ErrorResponse?, 
    transferred: TransactionResponseDto?, 
    onTransfer: () -> Unit
){
    Column {
        Button(
            onClick = onTransfer
        ) {
            Text(
                text = btnText,
                fontSize = 20.sp,
                modifier = Modifier.padding(16.dp)
            )
        }

        Text(
            text = when {
                isTransferring -> "transferring............."
                errorTransfer != null -> errorTransfer.toString()
                transferred != null -> transferred.toString()
                else -> "else"
            }
        )
    }
}
package com.example.bankingapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.bankingapp.models.transactions.TransactionSummary
import com.example.bankingapp.ui.components.FilterChipRow
import com.example.bankingapp.ui.components.TransactionRowTextField

@Composable
fun ViewAllTransactionScreen(
    status: List<String>,
    type: List<String>,
    modifier: Modifier = Modifier,
    transactions: List<TransactionSummary> = emptyList(),
    onTransactionClick: (Long) -> Unit,
    onApply: (Map<String, String?>) -> Unit
){
    var selectedStatus by rememberSaveable { mutableStateOf<String?>(null) }
    var selectedType by rememberSaveable{ mutableStateOf<String?>(null) }
    var selectedDate by rememberSaveable { mutableStateOf<String?>(null) }
    var selectedAccount by rememberSaveable { mutableStateOf<String?>(null) }


    Column(
        modifier = modifier
    ) {
        FilterChipRow(
            selectedStatus = selectedStatus,
            selectedType = selectedType,
            selectedTimeSpan = selectedDate,
            status = status,
            type = type,
            onStatusSelected = { selectedStatus = it},
            onTypeSelected = { selectedType = it },
            onDateSelected = { selectedDate = it },
        ) {
            onApply(
                mapOf(
                    "Status" to selectedStatus,
                    "Type" to selectedType,
                    "Date" to selectedDate,
                    "Account" to selectedAccount
                )
            )
        }

        LazyColumn {
            items(transactions){ transaction ->
                TransactionRowTextField(
                    transaction = transaction,
                    onTransactionClick = onTransactionClick
                )
            }
        }
    }
}
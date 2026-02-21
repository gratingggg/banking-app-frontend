package com.example.bankingapp.ui.screens

import android.R.attr.top
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bankingapp.ui.components.AccountRowTextField
import com.example.bankingapp.ui.components.GandhijiNotePhoto
import com.example.bankingapp.models.transactions.TransactionSummary
import com.example.bankingapp.ui.components.TransactionRowTextField

@Composable
fun CustomerAccountsScreen(
    modifier: Modifier = Modifier,
    accounts: List<Pair<String, String>> = emptyList(),
    transactions: List<TransactionSummary>? = null,
    onAccountClick: (String) -> Unit,
    onCheckBalance: (String) -> Unit,
    onViewAllTransactions: () -> Unit,
    onParticularTransactionClick: (Long) -> Unit
){
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        item {
            GandhijiNotePhoto()
        }

        if(accounts.isNotEmpty()){
            items(accounts){ item ->
                AccountRowTextField(
                    accountId = item.first,
                    accountType = item.second,
                    onAccountClick = {
                        onAccountClick(item.first)
                    }
                ) {
                    onCheckBalance(item.first)
                }
            }
        }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                Text(
                    text = "Transaction history",
                    fontSize = 24.sp,
                    modifier = Modifier.weight(1f)
                )

                Row(
                    modifier = Modifier
                        .clickable(onClick = onViewAllTransactions)
                        .padding(top = 8.dp)
                ) {
                    Text(
                        text = "See all",
                        style = TextStyle(
                            fontSize = 14.sp,
                            color = Color.Blue
                        ),
                        modifier = Modifier.padding(top = 5.dp)
                    )

                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowForwardIos,
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp)
                            .padding(6.dp),
                        tint = Color.Blue
                    )
                }
            }
        }

        if(transactions != null && transactions.isNotEmpty()){
            val max = if(transactions.size < 10) transactions.size else 10

            items(transactions.take(max)){ transaction ->
                TransactionRowTextField(
                    transaction = transaction,
                ) {
                    onParticularTransactionClick(it)
                }
            }
        }
    }
}
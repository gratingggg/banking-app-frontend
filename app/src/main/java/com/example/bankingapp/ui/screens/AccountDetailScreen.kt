package com.example.bankingapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.example.bankingapp.models.transactions.TransactionSummary
import com.example.bankingapp.ui.components.TransactionRowTextField

@Composable
fun AccountDetailScreen(
    accountId: String,
    accountStatus: String,
    accountType: String,
    dateOfIssuance: String,
    customerName: String,
    modifier: Modifier = Modifier,
    transactions: LazyPagingItems<TransactionSummary>,
    onViewAccountTransactions: () -> Unit,
    onParticularTransactionClick: (Long) -> Unit
) {
    LazyColumn(
        modifier = modifier
            .padding(8.dp)
    ) {
        item {
            Text(
                text = "Bank balance",
                style = MaterialTheme.typography.titleLarge,
                fontSize = 28.sp,
                modifier = Modifier
                    .padding(top = 55.dp)
                    .padding(horizontal = 36.dp)
            )
        }

        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                    top = 12.dp,
                    bottom = 24.dp,
                    start = 24.dp,
                    end = 24.dp
                    ),
                contentAlignment = Alignment.Center
            ){
                Card(
                    modifier = Modifier
                        .padding(8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF494848),
                        contentColor = Color(0xFFFFFFFF)
                    ),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.elevatedCardElevation(
                        defaultElevation = 8.dp
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(
                            text = "Account Id",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = accountId,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "Account Status",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = accountStatus,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.height(12.dp))


                        Text(
                            text = "Account Type",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = accountType,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.height(12.dp))


                        Text(
                            text = "Date of Issuance",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = dateOfIssuance,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.height(12.dp))


                        Text(
                            text = "Account holder name",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = customerName,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }
        }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Transaction history",
                    fontSize = 24.sp,
                    modifier = Modifier.weight(1f)
                )

                Row(
                    modifier = Modifier
                        .clickable(onClick = onViewAccountTransactions)
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

            val max = if(transactions.itemCount < 10) transactions.itemCount else 10
            items(
                count = max,
                key = transactions.itemKey(key = { it.transactionId })
            ){ index ->
                val transaction = transactions[index]
                if(transaction != null){
                    TransactionRowTextField(
                        transaction = transaction,
                    ) {
                        onParticularTransactionClick(it)
                    }
                }
            }
        }

}


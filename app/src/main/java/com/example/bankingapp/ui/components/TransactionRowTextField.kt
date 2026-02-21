package com.example.bankingapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bankingapp.models.transactions.TransactionSummary

@Composable
fun TransactionRowTextField(
    transaction: TransactionSummary,
    modifier: Modifier = Modifier,
    onTransactionClick: (Long) -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .clickable(onClick = {
                onTransactionClick(transaction.transactionId)
            }),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF2A2A2A)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
        ) {
            val firstLetter = if(transaction.otherCustomer != null) transaction.otherCustomer.first().uppercase() else "R"
            LetterCircleBox(
                letter = firstLetter,
                modifier = Modifier.weight(0.2f).padding(top = 4.dp, end = 8.dp)
            )

            Column(
                modifier = Modifier
                    .weight(0.7f),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = transaction.otherCustomer ?: "Personal Bank of Rudra",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                    ),
                    maxLines = Int.MAX_VALUE,
                    overflow = TextOverflow.Clip
                )

                Text(
                    text = transaction.dateOfTransaction,
                    fontSize = 14.sp,
                    color = Color.White
                )
            }

            Text(
                text = buildString {
                    if (transaction.credit) append("+ ")
                    append("₹")
                    append(transaction.amount)
                },
                style = TextStyle(
                    fontSize = 18.sp,
                    color = if (transaction.credit) Color.Green else Color.White
                )
            )
        }
    }
}

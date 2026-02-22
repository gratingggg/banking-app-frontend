package com.example.bankingapp.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bankingapp.ui.components.LetterCircleBox
import com.example.bankingapp.ui.components.TransactionDetailsCard
import com.example.bankingapp.utils.TransactionStatus

@Composable
fun TransactionDetailsScreen(
    accountId: String,
    customerName: String,
    selfName: String,
    amount: String,
    status: TransactionStatus,
    dateTime: String,
    transactionId: String,
    isCredit: Boolean,
    modifier: Modifier = Modifier,
    toAccountId: String? = null,
    failureReason: String? = null,
    fromAccountId: String? = null
){
    LazyColumn(
        modifier = modifier.padding(top = 96.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            LetterCircleBox(customerName.first().uppercase())
        }

        item {
            Log.d("rudraInTransactionDetailsScreen", isCredit.toString())
            Text(
                text = buildString {
                    append(

                        if(isCredit) "From" else "To"
                    )
                    append(": ")
                    append(customerName)
                },
                fontSize = 18.sp,
                modifier = Modifier.padding(8.dp)
            )
        }

        item {
            Text(
                text = "₹$amount",
                style = TextStyle(
                    fontSize = 36.sp,
                    fontWeight = FontWeight.SemiBold
                ),
                modifier = Modifier.padding(16.dp)
            )
        }

        item {
            Row(
                modifier = Modifier.padding(16.dp)
            ) {
                if(status == TransactionStatus.SUCCESS){
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "success",
                        tint = Color.Green
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = "failure",
                        tint = Color.Yellow
                    )
                }

                Text(
                    text = status.name.lowercase().replaceFirstChar { it -> it.uppercase() },
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Light
                )
            }
        }

        item {
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 200.dp),
                thickness = 1.dp,
                color = Color(0xFF333333)
            )

            Text(
                text = dateTime,
                fontSize = 14.sp,
                fontWeight = FontWeight.Light
            )
        }

        item {
            TransactionDetailsCard(
                accountId = accountId,
                transactionId = transactionId,
                senderName = if(isCredit) customerName else selfName,
                receiverName = if(isCredit) selfName else customerName,
                modifier = Modifier.padding(24.dp),
                toAccountId = toAccountId,
                fromAccountId = fromAccountId,
                failureReason = failureReason
            )
        }
    }
}
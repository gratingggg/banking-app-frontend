package com.example.bankingapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bankingapp.R

@Composable
fun AccountBalanceScreen(
    accountId: String,
    accountType: String,
    modifier: Modifier = Modifier,
    balance: String? = "0.00"
){
    Box(
        modifier = modifier
            .padding(bottom = 100.dp)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Card(
            modifier = Modifier
                .padding(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF494848),
                contentColor = Color(0xFFFFFFFF)
            ),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.elevatedCardElevation(
                defaultElevation = 8.dp
            )
        ) {
            Column {
                Text(
                    text = "₹$balance",
                    style = MaterialTheme.typography.titleLarge,
                    fontSize = 54.sp,
                    modifier = Modifier
                        .padding(28.dp)
                )

                Row(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(8.dp)
                            .weight(1f)
                    ) {
                        Text(
                            text = "Personal Bank of Rudra $accountId",
                            style = MaterialTheme.typography.bodySmall
                        )

                        Text(
                            text = "$accountType account",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Light
                        )
                    }

                    Image(
                        painter = painterResource(R.drawable.small_rudra_with_specs),
                        contentDescription = "logo",
                        modifier = Modifier
                            .size(84.dp)
                            .padding(8.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )
                }
            }
        }
    }
}
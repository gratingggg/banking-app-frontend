package com.example.bankingapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bankingapp.R

@Composable
fun TransactionDetailsCard(
    accountId: String,
    transactionId: String,
    senderName: String,
    receiverName: String,
    modifier: Modifier = Modifier,
    toAccountId: String? = null,
    fromAccountId: String? = null,
    failureReason: String? = null
){
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF6E6C6C),
            contentColor = Color.White
        )
    ){
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(R.drawable.small_rudra_with_specs),
                contentDescription = "logo",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(75.dp)
                    .height(50.dp)
                    .clip(RoundedCornerShape(12.dp))
            )

            Text(
                text = "Personal Bank of Rudra $accountId",
                fontSize = 20.sp,
                overflow = TextOverflow.Clip,
                maxLines = Int.MAX_VALUE
            )
        }

        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = Color(0xFF6E6C6C)
        )

        if(failureReason != null){
            Text(
                text = failureReason,
                fontSize = 18.sp,
                maxLines = Int.MAX_VALUE,
                overflow = TextOverflow.Clip
            )

            Divider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 1.dp,
                color = Color(0xFF6E6C6C)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Text(
                text = "Transaction ID",
                fontSize = 18.sp
            )

            Text(
                text = transactionId,
                fontSize = 18.sp,
                fontWeight = FontWeight.Light
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "To: $receiverName",
                fontSize = 18.sp
            )

            if(toAccountId != null) {
                Text(
                    text = toAccountId,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Light
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "To: $senderName",
                fontSize = 18.sp
            )

            if(fromAccountId != null) {
                Text(
                    text = fromAccountId,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Light
                )
            }
        }
    }
}
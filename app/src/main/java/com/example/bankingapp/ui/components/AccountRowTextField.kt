package com.example.bankingapp.ui.components

import android.R.attr.top
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bankingapp.R
import com.example.bankingapp.ui.theme.lightCoralPink

@Composable
fun AccountRowTextField(
    accountId: String,
    accountType: String,
    modifier: Modifier = Modifier,
    onAccountClick: () -> Unit,
    onCheckBalance: () -> Unit
){
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
//            containerColor = Color(0xFF2A2A2A)
            containerColor = lightCoralPink
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(R.mipmap.ic_my_logo_round),
                contentDescription = "bank logo",
                modifier = Modifier.weight(0.2f)
            )

            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .clickable(onClick = onAccountClick)
                    .weight(0.6f)
            ) {
                Text(
                    text = buildString {
                        append(stringResource(R.string.my_bank_name_for_account))
                        append(" $accountId")
                    },
                    style = TextStyle(
                        fontSize = 20.sp,
                        textAlign = TextAlign.Start,
                        color = Color.White
                    )
                )

                Text(
                    text = accountType,
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Light,
                        textAlign = TextAlign.Start,
                        color = Color.White
                    )
                )
            }

            Text(
                text = stringResource(R.string.check_balance),
                style = TextStyle(
                    fontSize = 14.sp,
                    color = Color.Blue
                ),
                modifier = Modifier
                    .padding(top = 4.dp)
                    .clickable(onClick = onCheckBalance)
                    .weight(0.2f)
            )
        }
    }
}
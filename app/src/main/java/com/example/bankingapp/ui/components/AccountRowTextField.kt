package com.example.bankingapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bankingapp.R

@Composable
fun AccountRowTextField(
    accountId: String,
    accountType: String,
    modifier: Modifier = Modifier,
    onAccountClick: () -> Unit,
    onCheckBalance: () -> Unit
){
    Row(
        modifier = modifier
            .padding(12.dp)
            .fillMaxWidth()
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(R.mipmap.ic_my_logo_round),
            contentDescription = "bank logo",
            modifier = Modifier.weight(0.2f)
        )

        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .clickable(onClick = onAccountClick)
                .weight(0.5f)
        ) {
            Text(
                text = buildString {
                    append(stringResource(R.string.my_bank_name_for_account))
                    append(" $accountId")
                },
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Start
                )
            )

            Text(
                text = accountType,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Light,
                    textAlign = TextAlign.Start
                )
            )
        }

        Text(
            text = stringResource(R.string.check_balance),
            style = TextStyle(
                fontSize = 16.sp,
                color = Color.Blue
            ),
            modifier = Modifier
                .clickable(onClick = onCheckBalance)
                .weight(0.3f)
        )
    }
}
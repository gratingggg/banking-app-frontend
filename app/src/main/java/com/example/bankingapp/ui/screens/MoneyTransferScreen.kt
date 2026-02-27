package com.example.bankingapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bankingapp.ui.components.NormalTextField

@Composable
fun MoneyTransferScreen(
    isErrorTriggered: Boolean,
    modifier: Modifier = Modifier,
    onTransferMoney: (Map<String, String>) -> Unit
) {
    var fromAccountId by rememberSaveable {
        mutableStateOf("")
    }
    var toAccountId by rememberSaveable {
        mutableStateOf("")
    }
    var amount by rememberSaveable {
        mutableStateOf("")
    }

    Column(
        modifier = modifier
            .padding(24.dp)
    ) {
        Text(
            text = "Bank transfer",
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .padding(
                    top = 48.dp,
                    start = 24.dp
                )
        )

        Spacer(modifier = Modifier.height(30.dp))

        Column(
            modifier = Modifier
                .padding(vertical = 48.dp)
        ) {
            NormalTextField(
                fieldValue = fromAccountId,
                fieldLabel = "Sender's bank a/c",
                isErrorTriggered = isErrorTriggered,
                enablePhoneOnlyKeyboard = true,
                errorMsg = null,
                textColor = Color.White
            ) {
                fromAccountId = it
            }

            Spacer(modifier = Modifier.padding(16.dp))

            NormalTextField(
                fieldValue = toAccountId,
                fieldLabel = "Receiver's bank a/c",
                isErrorTriggered = isErrorTriggered,
                enablePhoneOnlyKeyboard = true,
                errorMsg = null,
                textColor = Color.White
            ) {
                toAccountId = it
            }

            Spacer(modifier = Modifier.padding(16.dp))

            NormalTextField(
                fieldValue = amount,
                fieldLabel = "Amount",
                isErrorTriggered = isErrorTriggered,
                enablePhoneOnlyKeyboard = true,
                errorMsg = {
                    Text(
                        text = "Enter correct amount"
                    )
                },
                validate = {
                    it.all { it.isDigit() }
                },
                textColor = Color.White
            ) {
                amount = it
            }

            Spacer(modifier = Modifier.padding(16.dp))

            Button(
                onClick = {
                    onTransferMoney(
                        mapOf(
                            "FromAccountId" to fromAccountId,
                            "ToAccountId" to toAccountId,
                            "Amount" to amount
                        )
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                enabled = fromAccountId.isNotEmpty() && toAccountId.isNotEmpty() && amount.isNotEmpty()
            ) {
                Text(
                    text = "Continue",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}
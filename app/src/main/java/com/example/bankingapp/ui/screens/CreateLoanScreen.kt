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
import com.example.bankingapp.ui.components.NormalDropdownMenuBox
import com.example.bankingapp.ui.components.NormalTextField
import com.example.bankingapp.utils.LoanType

@Composable
fun CreateLoanScreen(
    isErrorTriggered: Boolean,
    modifier: Modifier = Modifier,
    onCreateLoan: (Map<String, String>) -> Unit
) {
    var accountId by rememberSaveable {
        mutableStateOf("")
    }
    var tenure by rememberSaveable {
        mutableStateOf("")
    }
    var principal by rememberSaveable {
        mutableStateOf("")
    }
    var type by rememberSaveable {
        mutableStateOf("")
    }
    Column(
        modifier = modifier
            .padding(24.dp)
    ) {
        Text(
            text = "Apply for loan",
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
                fieldValue = accountId,
                fieldLabel = "A/C no",
                isErrorTriggered = isErrorTriggered,
                enablePhoneOnlyKeyboard = true,
                errorMsg = null,
                textColor = Color.White
            ) {
                accountId = it
            }

            Spacer(modifier = Modifier.padding(16.dp))

            NormalTextField(
                fieldValue = principal,
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
                principal = it
            }

            Spacer(modifier = Modifier.height(16.dp))

            NormalDropdownMenuBox(
                fieldValue = type,
                fieldLabel = "Loan type",
                options = LoanType.entries.map { it.name.lowercase().replaceFirstChar { it.uppercase() } },
                isErrorTriggered = isErrorTriggered,
                imageVector = null
            ) {
                type = it
            }

            Spacer(modifier = Modifier.padding(16.dp))

            NormalTextField(
                fieldValue = tenure,
                fieldLabel = "Tenure in months",
                isErrorTriggered = isErrorTriggered,
                enablePhoneOnlyKeyboard = true,
                textColor = Color.White
            ) {
                tenure = it
            }

            Spacer(modifier = Modifier.padding(16.dp))

            Button(
                onClick = {
                    onCreateLoan(
                        mapOf(
                            "AccountId" to accountId,
                            "Principal" to principal,
                            "Type" to type,
                            "Tenure" to tenure
                        )
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                enabled = accountId.isNotEmpty() && principal.isNotEmpty()
                        && tenure.isNotEmpty() && type.isNotEmpty()
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

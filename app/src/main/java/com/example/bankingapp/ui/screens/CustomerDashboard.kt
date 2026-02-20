package com.example.bankingapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.AddCard
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Savings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.bankingapp.R
import com.example.bankingapp.ui.components.RowTextField
import com.example.bankingapp.ui.components.ShamshanGhaatImage
import com.example.bankingapp.ui.components.SmallItems

@Composable
fun CustomerDashboardScreen(
    modifier: Modifier = Modifier,
    onBankTransfer: () -> Unit,
    onViewAllLoans: () -> Unit,
    onCreateLoan: () -> Unit,
    onCreateAccount: () -> Unit,
    onCustomerAllTransaction: () -> Unit,
    onCustomerAllNotification: () -> Unit,
    onViewBalance: () -> Unit
) {
    Column(
        modifier = modifier
    ){
        ShamshanGhaatImage()

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 48.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            SmallItems(
                fieldLabel = "Create\nAccount",
                imageVector = Icons.Default.AddCard,
                iconDescription = "Create account"
            ) {
                onCreateAccount()
            }

            SmallItems(
                fieldLabel = "Money\nTransfer",
                imageVector = ImageVector.vectorResource(R.drawable.ic_send_money),
                iconDescription = "Transfer"
            ) {
                onBankTransfer()
            }

            SmallItems(
                fieldLabel = "View\nLoans",
                imageVector = Icons.Default.Description,
                iconDescription = "view loans"
            ) {
                onViewAllLoans()
            }

            SmallItems(
                fieldLabel = "Loan\nEMI",
                imageVector = Icons.Default.Savings,
                iconDescription = "create loan"
            ) {
                onCreateLoan()
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    bottom = 24.dp
                )
        ){
            RowTextField(
                fieldLabel = "See transaction history",
                imageVector = Icons.Default.History
            ) {
                onCustomerAllTransaction()
            }

            RowTextField(
                fieldLabel = "See notification history",
                imageVector = Icons.Default.Notifications
            ) {
                onCustomerAllNotification()
            }

            RowTextField(
                fieldLabel = "Check bank balance",
                imageVector = Icons.Default.AccountBalance
            ) {
                onViewBalance()
            }
        }
    }
}
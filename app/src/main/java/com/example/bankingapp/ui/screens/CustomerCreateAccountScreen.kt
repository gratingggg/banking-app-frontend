package com.example.bankingapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.bankingapp.ui.components.NormalDropdownMenuBox
import com.example.bankingapp.utils.AccountType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerCreateAccountScreen(
    isErrorTriggered: Boolean,
    modifier: Modifier = Modifier,
    onContinue: (String) -> Unit
){

    var accountType by rememberSaveable {
        mutableStateOf("")
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(
            text = "Create Account",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(100.dp))

        Text(
            text = "Account Type",
            style = MaterialTheme.typography.titleMedium
        )

        NormalDropdownMenuBox(
            fieldValue = accountType,
            fieldLabel = "Account Type",
            options = AccountType.entries.map { it.name.lowercase().replaceFirstChar { it.uppercase() } },
            imageVector = null,
            isErrorTriggered = isErrorTriggered
        ) {
            onContinue(it)
        }
    }
}

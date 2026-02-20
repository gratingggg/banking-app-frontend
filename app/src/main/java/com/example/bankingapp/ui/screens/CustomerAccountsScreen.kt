package com.example.bankingapp.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.bankingapp.ui.components.AccountRowTextField
import com.example.bankingapp.ui.components.GandhijiNotePhoto

@Composable
fun CustomerAccountsScreen(
    modifier: Modifier = Modifier,
    accounts: List<Pair<String, String>> = emptyList(),
    transactions: List<>
    onAccountClick: (String) -> Unit,
    onCheckBalance: (String) -> Unit
){
    LazyColumn {
        item {
            GandhijiNotePhoto()
        }

        if(accounts.isNotEmpty()){
            items(accounts){ item ->
                AccountRowTextField(
                    accountId = item.first,
                    accountType = item.second,
                    onAccountClick = {
                        onAccountClick(item.first)
                    }
                ) {
                    onCheckBalance(item.first)
                }
            }
        }


    }
}
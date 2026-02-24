package com.example.bankingapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun TransactionSkeletonList(
    itemCount: Int = 10
){
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        repeat(itemCount) {
            TransactionSkeletonCard()
        }
    }
}
package com.example.bankingapp.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.paging.compose.LazyPagingItems
import com.example.bankingapp.models.notifications.NotificationResponseDto

@Composable
fun CustomerAllNotifications(
    notifications: LazyPagingItems<NotificationResponseDto>,
    modifier: Modifier = Modifier
){
    LazyColumn {

    }
}
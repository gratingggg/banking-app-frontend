package com.example.bankingapp.ui.screens.customer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.example.bankingapp.models.notifications.NotificationResponseDto
import com.example.bankingapp.ui.components.FilterChipRow
import com.example.bankingapp.ui.components.NotificationItem
import com.example.bankingapp.ui.components.StaggeredItem
import com.example.bankingapp.ui.components.TransactionListSection
import com.example.bankingapp.utils.NotificationStatus

@Composable
fun CustomerAllNotificationsScreen(
    notifications: LazyPagingItems<NotificationResponseDto>,
    status: List<String>,
    type: List<String>,
    modifier: Modifier = Modifier,
    readAllFailed: Boolean,
    onNotificationClick: (Long) -> Unit,
    onReadAll: () -> Unit,
    onApply: (Map<String, String?>) -> Unit,
) {
    var hasUnread = (0 until notifications.itemCount).any { index ->
        notifications[index]?.notificationStatus == NotificationStatus.UNREAD
    }

    var checked by rememberSaveable {
        mutableStateOf(false)
    }

    LaunchedEffect(hasUnread) {
        if (hasUnread) checked = false
    }

    LaunchedEffect(readAllFailed) {
        if(readAllFailed) checked = false
    }

    val isChecked = checked || !hasUnread


    var selectedStatus by rememberSaveable { mutableStateOf<String?>(null) }
    var selectedType by rememberSaveable{ mutableStateOf<String?>(null) }
    var selectedDate by rememberSaveable { mutableStateOf<String?>(null) }

    Column(
        modifier = modifier.fillMaxSize()
    ) {

        FilterChipRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            selectedStatus = selectedStatus,
            selectedType = selectedType,
            selectedTimeSpan = selectedDate,
            status = status,
            type = type,
            onStatusSelected = { selectedStatus = it },
            onTypeSelected = { selectedType = it },
            onDateSelected = { selectedDate = it },
            onApply = {
                onApply(
                    mapOf(
                        "Status" to selectedStatus,
                        "Type" to selectedType,
                        "Date" to selectedDate
                    )
                )
            },
        ) {
            onApply(
                mapOf(
                    "Status" to selectedStatus,
                    "Type" to selectedType,
                    "Date" to selectedDate
                )
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 24.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = "Read all",
                modifier = Modifier.padding(top = 12.dp)
            )

            Checkbox(
                checked = isChecked,
                onCheckedChange = {
                    if (!isChecked) {
                        checked = true
                        onReadAll()
                    }
                },
                enabled = !isChecked
            )
        }

        TransactionListSection(
            isLoading = notifications.loadState.refresh is LoadState.Loading ||
                    notifications.loadState.append is LoadState.Loading
        ) {
            LazyColumn {
                items(
                    count = notifications.itemCount,
                    key = notifications.itemKey(key = { it.notificationId })
                ) { index ->
                    notifications[index]?.let {
                        StaggeredItem(
                            index = index
                        ) {
                            NotificationItem(
                                notification = it
                            ) {
                                onNotificationClick(it)
                            }
                        }
                    }
                }
            }
        }
    }
}


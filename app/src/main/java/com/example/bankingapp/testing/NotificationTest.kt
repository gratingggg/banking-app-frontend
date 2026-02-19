package com.example.bankingapp.testing

import android.widget.Space
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bankingapp.models.exception.ErrorResponse
import com.example.bankingapp.models.notifications.NotificationPagedResultDto
import com.example.bankingapp.models.notifications.NotificationResponseDto
import com.example.bankingapp.viewmodel.NotificationViewModel
import java.nio.file.WatchEvent

@Composable
fun TestingNotificationApi(viewModel: NotificationViewModel){
    val state by viewModel.uiState.collectAsState()

    var notificationId by remember {
        mutableLongStateOf(234)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(24.dp)
    ) {
        GetNotification(
            isLoadingNotification = state.isLoadingNotification,
            errorNotification = state.errorNotification,
            notification = state.notification
        ) {
            viewModel.getNotification(notificationId)
        }

        Spacer(modifier = Modifier.height(16.dp))

        GetAllNotifications(
            isLoadingAllNotifications = state.isLoadingAllNotifications,
            errorAllNotifications = state.errorAllNotifications,
            notifications = state.allNotifications,
            onGetAllNotifications = {
                viewModel.getAllNotifications()
            }
        ) {
            notificationId = it
        }

        Spacer(modifier = Modifier.height(16.dp))

        ReadAllNotifications(
            isReading = state.isReadingNotifications,
            errorReading = state.errorReadNotifications,
        ) {
            viewModel.readAllNotifications()
        }
    }
}


@Composable
fun GetNotification(
    isLoadingNotification: Boolean,
    errorNotification: ErrorResponse?,
    notification: NotificationResponseDto?,
    onGetNotification: () -> Unit
){
    Column {
        Button(
            onClick = onGetNotification
        ) {
            Text(
                text = "Get Notification",
                fontSize = 20.sp,
                modifier = Modifier.padding(16.dp)
            )
        }

        Text(
            text = when {
                isLoadingNotification -> "loading notification..........."
                errorNotification != null -> errorNotification.toString()
                notification != null -> notification.toString()
                else -> "else"
            }
        )
    }
}

@Composable
fun GetAllNotifications(
    isLoadingAllNotifications: Boolean,
    errorAllNotifications: ErrorResponse?,
    notifications: NotificationPagedResultDto?,
    onGetAllNotifications: () -> Unit,
    onAllNotifications: (Long) -> Unit
){
    Column {
        Button(
            onClick = onGetAllNotifications
        ) {
            Text(
                text = "Get all notifications",
                fontSize = 20.sp,
                modifier = Modifier.padding(16.dp)
            )
        }

        Text(
            text = when {
                isLoadingAllNotifications -> "loading all notifications......."
                errorAllNotifications != null -> errorAllNotifications.toString()
                notifications == null -> "null"
                notifications.content.isEmpty() -> "no notifications"
                else -> notifications.toString()
            }
        )
    }

    notifications?.content?.let {
        LaunchedEffect(it) {
            onAllNotifications(it.last().notificationId)
        }
    }
}

@Composable
fun ReadAllNotifications(
    isReading: Boolean,
    errorReading: ErrorResponse?,
    onReadAllNotifications: () -> Unit
){
    Column {
        Button(
            onClick = onReadAllNotifications
        ) {
            Text(
                text = "Read All Notifications",
                fontSize = 20.sp,
                modifier = Modifier.padding(16.dp)
            )
        }

        Text(
            text = when {
                isReading -> "reading all notifications"
                errorReading != null -> errorReading.toString()
                else -> "else"
            }
        )
    }
}
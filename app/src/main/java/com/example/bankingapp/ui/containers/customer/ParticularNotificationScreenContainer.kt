package com.example.bankingapp.ui.containers.customer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import com.example.bankingapp.network.RetrofitInstance
import com.example.bankingapp.repository.notifications.NotificationRepositoryImpl
import com.example.bankingapp.ui.screens.customer.ParticularNotificationScreen
import com.example.bankingapp.ui.theme.veryLightCoralPink
import com.example.bankingapp.utils.Constants
import com.example.bankingapp.viewmodel.NotificationViewModel
import com.example.bankingapp.viewmodel.factory.NotificationViewModelFactory
import java.time.format.DateTimeFormatter

@Composable
fun ParticularNotificationScreenContainer(
    entry: NavBackStackEntry,
    notificationId: String,
    modifier: Modifier = Modifier
){
    val viewModel: NotificationViewModel = viewModel(
        viewModelStoreOwner = entry,
        factory = NotificationViewModelFactory(
            notificationRepository = NotificationRepositoryImpl(RetrofitInstance.notificationApiService)
        )
    )
    val state by viewModel.uiState.collectAsState()

    val snackbar = remember {
        SnackbarHostState()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = veryLightCoralPink),
    ){
        val notification = state.notification
        notification?.let {
            ParticularNotificationScreen(
                type = it.notificationType.toString().lowercase().replaceFirstChar { it.uppercase() },
                message = it.message,
                time = it.date.format(DateTimeFormatter.ofPattern(Constants.LOCAL_DATE_TIME_PATTERN)),
                modifier = Modifier
                    .padding(vertical = 200.dp)
            )
        }

        SnackbarHost(
            hostState = snackbar,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.BottomCenter)
        )
    }

    LaunchedEffect(state) {
        state.errorNotification?.message?.let {
            snackbar.showSnackbar(
                message = it,
                withDismissAction = true,
                duration = SnackbarDuration.Short
            )
        }
    }

    LaunchedEffect(Unit) {
        viewModel.getNotification(notificationId)
    }
}
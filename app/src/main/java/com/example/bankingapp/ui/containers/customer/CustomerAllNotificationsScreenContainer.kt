package com.example.bankingapp.ui.containers.customer

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
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.bankingapp.navigation.AppDestinations
import com.example.bankingapp.navigation.navigateAndClear
import com.example.bankingapp.network.RetrofitInstance
import com.example.bankingapp.repository.notifications.NotificationRepositoryImpl
import com.example.bankingapp.ui.screens.CustomerAllNotificationsScreen
import com.example.bankingapp.utils.NotificationStatus
import com.example.bankingapp.utils.NotificationType
import com.example.bankingapp.viewmodel.NotificationViewModel
import com.example.bankingapp.viewmodel.factory.NotificationViewModelFactory

@Composable
fun CustomerAllNotificationsScreenContainer(
    navController: NavHostController,
    entry: NavBackStackEntry,
    modifier: Modifier = Modifier
) {
    val viewModel: NotificationViewModel = viewModel(
        viewModelStoreOwner = entry,
        factory = NotificationViewModelFactory(
            notificationRepository = NotificationRepositoryImpl(RetrofitInstance.notificationApiService)
        )
    )
    val state by viewModel.uiState.collectAsState()
    val notifications = viewModel.notifications.collectAsLazyPagingItems()

    val snackbar = remember {
        SnackbarHostState()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        CustomerAllNotificationsScreen(
            notifications = notifications,
            readAllFailed = state.errorReadNotifications != null,
            status = NotificationStatus.entries.map {
                it.name.lowercase().replaceFirstChar { it.uppercase() }
            },
            type = NotificationType.entries.map {
                it.name.lowercase().replaceFirstChar { it.uppercase() }
            },
            onNotificationClick = {
                navController.navigateAndClear(
                    route = AppDestinations.ParticularNotificationScreen.particularNotificationRoute(it.toString())
                )
            },
            onReadAll = {
                viewModel.readAllNotifications()
            }
        ) {
            viewModel.getAllNotifications(
                notificationStatusStr = it["Status"],
                notificationTypeStr = it["Type"],
                fromDateStr = it["Date"]
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

    LaunchedEffect(state, notifications.loadState) {
        val notificationsError = notifications.loadState.refresh as? LoadState.Error
            ?: notifications.loadState.append as? LoadState.Error

        val readError = state.errorReadNotifications

        notificationsError?.error?.message?.let {
            snackbar.showSnackbar(
                message = it,
                withDismissAction = true,
                duration = SnackbarDuration.Short
            )
        }

        readError?.message?.let {
            snackbar.showSnackbar(
                message = it,
                withDismissAction = true,
                duration = SnackbarDuration.Short
            )
        }
    }

    LaunchedEffect(Unit) {
        viewModel.getAllNotifications()
    }
}
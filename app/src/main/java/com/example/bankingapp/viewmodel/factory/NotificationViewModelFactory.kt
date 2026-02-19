package com.example.bankingapp.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bankingapp.repository.notifications.NotificationRepository
import com.example.bankingapp.viewmodel.NotificationViewModel

class NotificationViewModelFactory(
    private val notificationRepository: NotificationRepository
): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NotificationViewModel(notificationRepository) as T
    }
}
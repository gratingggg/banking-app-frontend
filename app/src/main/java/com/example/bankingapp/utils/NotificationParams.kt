package com.example.bankingapp.utils

data class NotificationParams(
    val notificationStatus: NotificationStatus? = null,
    val notificationType: NotificationType? = null,
    val fromDate: String? = null,
    val toDate: String? = null,
    val refreshKey: Long = System.currentTimeMillis()
)
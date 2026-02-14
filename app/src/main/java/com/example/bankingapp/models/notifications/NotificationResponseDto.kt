package com.example.bankingapp.models.notifications

import com.example.bankingapp.utils.NotificationStatus
import com.example.bankingapp.utils.NotificationType
import com.squareup.moshi.JsonClass
import java.time.LocalDateTime

@JsonClass(generateAdapter = true)
data class NotificationResponseDto(
    val notificationId: Long,
    val message: String,
    val date: LocalDateTime,
    val notificationStatus: NotificationStatus,
    val notificationType: NotificationType
)

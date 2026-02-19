package com.example.bankingapp.repository.notifications

import com.example.bankingapp.models.notifications.NotificationPagedResultDto
import com.example.bankingapp.models.notifications.NotificationResponseDto
import com.example.bankingapp.utils.ApiResult
import com.example.bankingapp.utils.NotificationStatus
import com.example.bankingapp.utils.NotificationType
import java.time.LocalDate

interface NotificationRepository {
    suspend fun getAllNotifications(
        page: Int? = null,
        size: Int? = null,
        notificationStatus: NotificationStatus? = null,
        notificationType: NotificationType? = null,
        fromDate: LocalDate? = null,
        toDate: LocalDate? = null
    ): ApiResult<NotificationPagedResultDto>

    suspend fun getANotification(notificationId: Long): ApiResult<NotificationResponseDto>

    suspend fun readAllNotification(): ApiResult<Unit>
}
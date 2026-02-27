package com.example.bankingapp.repository.notifications

import androidx.paging.PagingData
import com.example.bankingapp.models.PagedResponse
import com.example.bankingapp.models.notifications.NotificationResponseDto
import com.example.bankingapp.utils.ApiResult
import com.example.bankingapp.utils.NotificationStatus
import com.example.bankingapp.utils.NotificationType
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface NotificationRepository {
    suspend fun getAllNotifications(
        notificationStatus: NotificationStatus? = null,
        notificationType: NotificationType? = null,
        fromDate: String? = null,
        toDate: String? = null
    ): Flow<PagingData<NotificationResponseDto>>

    suspend fun getANotification(notificationId: Long): ApiResult<NotificationResponseDto>

    suspend fun readAllNotification(): ApiResult<Unit>
}
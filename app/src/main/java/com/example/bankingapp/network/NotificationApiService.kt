package com.example.bankingapp.network

import com.example.bankingapp.models.notifications.NotificationPagedResultDto
import com.example.bankingapp.models.notifications.NotificationResponseDto
import com.example.bankingapp.utils.Endpoints
import com.example.bankingapp.utils.NotificationStatus
import com.example.bankingapp.utils.NotificationType
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import java.time.LocalDate

interface NotificationApiService {
    @GET(Endpoints.NOTIFICATIONS_ALL)
    suspend fun getAllNotifications(
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null,
        @Query("type") notificationType: NotificationType? = null,
        @Query("status") notificationStatus: NotificationStatus? = null,
        @Query("fromDate") fromDate: LocalDate? = null,
        @Query("toDate") toDate: LocalDate? = null
    ): Response<NotificationPagedResultDto>

    @GET(Endpoints.NOTIFICATION_PARTICULAR)
    suspend fun getANotification(@Path("notificationId") notificationId: Long)
    : Response<NotificationResponseDto>

    @PUT(Endpoints.NOTIFICATION_READ_ALL)
    suspend fun readAllNotifications(): Response<Unit>
}
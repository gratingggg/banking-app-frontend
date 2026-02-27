package com.example.bankingapp.repository.notifications

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.bankingapp.models.PagedResponse
import com.example.bankingapp.models.exception.ErrorResponse
import com.example.bankingapp.models.notifications.NotificationResponseDto
import com.example.bankingapp.network.NotificationApiService
import com.example.bankingapp.network.RetrofitInstance
import com.example.bankingapp.paging.MyPagingSource
import com.example.bankingapp.utils.ApiResult
import com.example.bankingapp.utils.Constants
import com.example.bankingapp.utils.NotificationStatus
import com.example.bankingapp.utils.NotificationType
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import retrofit2.Response
import java.time.LocalDate

class NotificationRepositoryImpl(
    private val notificationApiService: NotificationApiService
): NotificationRepository {
    private fun <T> helper(result: Response<T>): ApiResult<T> {
        return try {
            if (result.isSuccessful) {
                @Suppress("UNCHECKED_CAST")
                ApiResult.Success(result.body() ?: Unit as T)
            } else {
                val error = RetrofitInstance.parseError(result.errorBody())
                ApiResult.Failure(error!!)
            }
        } catch (e: Exception) {
            ApiResult.Failure(
                ErrorResponse(
                    message = e.message.toString(),
                    statusCode = 6969
                )
            )
        }
    }

    override suspend fun getAllNotifications(
        notificationStatus: NotificationStatus?,
        notificationType: NotificationType?,
        fromDate: String?,
        toDate: String?
    ): Flow<PagingData<NotificationResponseDto>> {
        return Pager(
            config = PagingConfig(
                pageSize = Constants.PAGE_SIZE
            ),
            pagingSourceFactory = {
                MyPagingSource(
                    fetchData = { page ->
                        val result = notificationApiService.getAllNotifications(
                            page = page,
                            size = Constants.PAGE_SIZE,
                            notificationType = notificationType,
                            notificationStatus = notificationStatus,
                            fromDate = fromDate,
                            toDate = toDate
                        )

                        if(result.isSuccessful){
                            result.body() ?: throw Exception("Result body is null")
                        } else {
                            throw HttpException(result)
                        }
                    }
                )
            }
        ).flow
    }

    override suspend fun getANotification(notificationId: Long): ApiResult<NotificationResponseDto> {
        val result = notificationApiService.getANotification(notificationId)
        return helper(result)
    }

    override suspend fun readAllNotification(): ApiResult<Unit> {
        val result = notificationApiService.readAllNotifications()
        return helper(result)
    }
}
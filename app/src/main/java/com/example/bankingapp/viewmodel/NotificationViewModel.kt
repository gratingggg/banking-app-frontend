package com.example.bankingapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bankingapp.models.exception.ErrorResponse
import com.example.bankingapp.models.notifications.NotificationPagedResultDto
import com.example.bankingapp.models.notifications.NotificationResponseDto
import com.example.bankingapp.repository.notifications.NotificationRepository
import com.example.bankingapp.utils.ApiResult
import com.example.bankingapp.utils.NotificationStatus
import com.example.bankingapp.utils.NotificationType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

class NotificationViewModel(
    private val notificationRepository: NotificationRepository
): ViewModel(){
    private val _uiState = MutableStateFlow(NotificationUiState())
    val uiState: StateFlow<NotificationUiState> = _uiState

    fun getAllNotifications(
        page: Int? = null,
        size: Int? = null,
        notificationStatus: NotificationStatus? = null,
        notificationType: NotificationType? = null,
        fromDate: LocalDate? = null,
        toDate: LocalDate? = null
    ){
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoadingAllNotifications = true
                )
            }

            _uiState.update {
                when(val result = notificationRepository.getAllNotifications(
                    page = page, size = size, notificationStatus = notificationStatus,
                    notificationType = notificationType, fromDate = fromDate, toDate = toDate
                )){
                    is ApiResult.Failure -> it.copy(
                        errorAllNotifications = result.error,
                        isLoadingAllNotifications = false
                    )

                    is ApiResult.Success -> it.copy(
                        allNotifications = result.data,
                        errorAllNotifications = null,
                        isLoadingAllNotifications = false
                    )
                }
            }
        }
    }

    fun getNotification(notificationId: Long){
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoadingNotification = true
                )
            }

            _uiState.update {
                when(val result = notificationRepository.getANotification(notificationId)){
                    is ApiResult.Failure -> it.copy(
                        errorNotification = result.error,
                        isLoadingNotification = false
                    )

                    is ApiResult.Success -> it.copy(
                        notification = result.data,
                        errorNotification = null,
                        isLoadingNotification = false
                    )
                }
            }
        }
    }

    fun readAllNotifications(){
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isReadingNotifications = true
                )
            }

            _uiState.update {
                when(val result = notificationRepository.readAllNotification()){
                    is ApiResult.Failure -> it.copy(
                        errorReadNotifications = result.error,
                        isReadingNotifications = false
                    )

                    is ApiResult.Success -> it.copy(
                        errorReadNotifications = null,
                        isReadingNotifications = false
                    )
                }
            }
        }
    }
}

data class NotificationUiState(
    val isLoadingAllNotifications: Boolean = false,
    val isLoadingNotification: Boolean = false,
    val isReadingNotifications: Boolean = false,

    val allNotifications: NotificationPagedResultDto? = null,
    val notification: NotificationResponseDto? = null,

    val errorAllNotifications: ErrorResponse? = null,
    val errorNotification: ErrorResponse? = null,
    val errorReadNotifications: ErrorResponse? = null
)
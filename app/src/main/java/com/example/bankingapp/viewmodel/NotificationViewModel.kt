package com.example.bankingapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bankingapp.models.PagedResponse
import com.example.bankingapp.models.exception.ErrorResponse
import com.example.bankingapp.models.notifications.NotificationResponseDto
import com.example.bankingapp.repository.notifications.NotificationRepository
import com.example.bankingapp.utils.ApiResult
import com.example.bankingapp.utils.NotificationParams
import com.example.bankingapp.utils.NotificationStatus
import com.example.bankingapp.utils.NotificationType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

class NotificationViewModel(
    private val notificationRepository: NotificationRepository
): ViewModel(){
    private val _uiState = MutableStateFlow(NotificationUiState())
    val uiState: StateFlow<NotificationUiState> = _uiState

    private val _filter = MutableStateFlow<NotificationParams?>(null)
    @OptIn(ExperimentalCoroutinesApi::class)
    val notifications = _filter.flatMapLatest { filter ->
        if(filter == null) emptyFlow()
        else {
            notificationRepository.getAllNotifications(
                notificationStatus = filter.notificationStatus,
                notificationType = filter.notificationType,
                fromDate = filter.fromDate,
                toDate = filter.toDate,
            )
        }
    }

    fun getAllNotifications(
        notificationStatusStr: String? = null,
        notificationTypeStr: String? = null,
        fromDateStr: String? = null
    ){
        val notificationStatus = notificationStatusStr?.let {
            NotificationStatus.valueOf(it.uppercase())
        }

        val notificationType = notificationTypeStr?.let {
            NotificationType.valueOf(it.uppercase())
        }

        val monthsToMinus: Long = when (fromDateStr) {
            "This month" -> 1
            "Last 90 days" -> 3
            "Last 180 days" -> 6
            else -> 0
        }

        val fromDate =
            if (monthsToMinus.toInt() == 0) null else LocalDate.now().minusMonths(monthsToMinus)
                .toString()

        _filter.value = NotificationParams(
            notificationStatus = notificationStatus,
            notificationType = notificationType,
            fromDate = fromDate
        )
    }

    fun getNotification(notificationId: String){
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoadingNotification = true
                )
            }

            _uiState.update {
                when(val result = notificationRepository.getANotification(notificationId.toLong())){
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

                    is ApiResult.Success -> {
                        _filter.update { it?.copy(refreshKey = System.currentTimeMillis()) }
                        it.copy(
                            errorReadNotifications = null,
                            isReadingNotifications = false
                        )
                    }
                }
            }
        }
    }
}

data class NotificationUiState(
    val isLoadingNotification: Boolean = false,
    val isReadingNotifications: Boolean = false,

    val notification: NotificationResponseDto? = null,

    val errorNotification: ErrorResponse? = null,
    val errorReadNotifications: ErrorResponse? = null
)
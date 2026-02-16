package com.example.bankingapp.models.notifications

import com.example.bankingapp.utils.Page

data class NotificationPagedResultDto (
    val content: List<NotificationResponseDto>,
    val page: Page
)
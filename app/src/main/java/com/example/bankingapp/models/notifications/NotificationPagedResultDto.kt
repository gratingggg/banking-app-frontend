package com.example.bankingapp.models.notifications

data class NotificationPagedResultDto (
    val content: List<NotificationResponseDto>,
    val number: Int,
    val size: Int,
    val totalPages: Int,
    val totalElements: Int
)
package com.example.bankingapp.models

data class PagedResultDto<T> (
    val content: List<T>,
    val number: Int,
    val size: Int,
    val totalPages: Int,
    val totalElements: Int
)
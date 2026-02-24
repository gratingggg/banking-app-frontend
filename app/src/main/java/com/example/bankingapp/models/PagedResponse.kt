package com.example.bankingapp.models

import com.example.bankingapp.utils.Page

data class PagedResponse<T>(
    val content: List<T>,
    val page: Page
)

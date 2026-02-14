package com.example.bankingapp.utils

import com.example.bankingapp.models.exception.ErrorResponse

sealed class ApiResult<out T>{
    data class Success<T>(var data: T): ApiResult<T>()
    data class Failure(var error: ErrorResponse): ApiResult<Nothing>()
}
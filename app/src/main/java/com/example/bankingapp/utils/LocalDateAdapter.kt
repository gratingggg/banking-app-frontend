package com.example.bankingapp.utils

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class LocalDateAdapter {
    private val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
    @ToJson
    fun toJson(date: LocalDate) = date.format(formatter)

    @FromJson
    fun fromJson(dateStr: String) = LocalDate.parse(dateStr, formatter)
}
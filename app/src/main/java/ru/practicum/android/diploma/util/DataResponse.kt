package ru.practicum.android.diploma.util

data class DataResponse<T>(
    val data: List<T>?,
    val networkError: String?
)

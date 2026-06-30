package ru.practicum.android.diploma.data.dto

data class Response<T>(
    val data: T?,
    val resultCode: Int = 0,
)

package ru.practicum.android.diploma.data.dto

data class Response<T>(
    val data: T?,
    var resultCode: Int = 0,
)

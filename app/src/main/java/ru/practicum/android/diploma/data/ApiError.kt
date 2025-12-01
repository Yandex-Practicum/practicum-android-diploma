package ru.practicum.android.diploma.data

class ApiError(val code: Int, override val message: String = "API error: $code") : Exception(message)

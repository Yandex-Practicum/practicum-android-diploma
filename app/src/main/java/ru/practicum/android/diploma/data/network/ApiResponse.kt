package ru.practicum.android.diploma.data.network

sealed class ApiResponse<T> {
    data class Success<T>(val data: T) : ApiResponse<T>()
    class Error<T> : ApiResponse<T>()
}

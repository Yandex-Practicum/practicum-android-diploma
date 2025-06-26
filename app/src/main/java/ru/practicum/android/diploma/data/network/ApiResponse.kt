package ru.practicum.android.diploma.data.network

sealed class ApiResponse<T> {
    class Success<T>(val data: T) : ApiResponse<T>()
    class Error<T>(val statusCode: Int, val data: T? = null) : ApiResponse<T>()
}

package ru.practicum.android.diploma.data.network

sealed class ApiResponse<T>(val data: T? = null, val page: Int, val pages: Int, val statusCode: Int? = null) {
    class Success<T>(data: T, page: Int, pages: Int) : ApiResponse<T>(data, page, pages)
    class Error<T>(statusCode: Int?, data: T? = null) : ApiResponse<T>(data, 0, 0, statusCode)
}

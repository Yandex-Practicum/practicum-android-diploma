package ru.practicum.android.diploma.data.network

sealed class ApiResponse<T>(
    val data: T? = null,
    val page: Int,
    val pages: Int,
    val found: Int,
    val statusCode: Int? = null
) {
    class Success<T>(data: T, page: Int, pages: Int, found: Int) : ApiResponse<T>(data, page, pages, found)
    class Error<T>(statusCode: Int?, data: T? = null) : ApiResponse<T>(data, 0, 0, 0, statusCode)
}

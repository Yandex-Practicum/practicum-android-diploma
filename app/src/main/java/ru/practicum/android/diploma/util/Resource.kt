package ru.practicum.android.diploma.util

sealed class Resource<T>(val data: T? = null, val page: Int, val pages: Int, val errorMessage: String? = null) {
    class Success<T>(data: T, page: Int, pages: Int) : Resource<T>(data, page, pages)
    class Error<T>(errorMessage: String, data: T? = null) : Resource<T>(data, 0, 0, errorMessage)
}

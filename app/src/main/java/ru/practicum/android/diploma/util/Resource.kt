package ru.practicum.android.diploma.util

sealed class Resource<T>(val data: T? = null, val errorMessage: String? = null) {
    class Success<T>(data: T): Resource<T>(data)
    class Error<T>(errorMessage: String, data: T? = null): Resource<T>(data, errorMessage)
}

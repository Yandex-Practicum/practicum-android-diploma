package ru.practicum.android.diploma.util

sealed interface ResponseData<T> {
    data class Data<T>(val value: T) : ResponseData<T>
    data class Error<T>(val error: ResponseError) : ResponseData<T>

    enum class ResponseError {
        NO_INTERNET,
        CLIENT_ERROR,
        SERVER_ERROR,
        NOT_FOUND
    }
}

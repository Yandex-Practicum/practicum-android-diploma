package ru.practicum.android.diploma.data.network

sealed class Response<out T> {
    data class Success<T>(val data: T) : Response<T>()
    object NoConnection : Response<Nothing>()
    object BadRequest : Response<Nothing>()
    object ServerError : Response<Nothing>()
    object NotFound : Response<Nothing>()
}

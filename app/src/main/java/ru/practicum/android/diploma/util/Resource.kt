package ru.practicum.android.diploma.util

import ru.practicum.android.diploma.util.network.HttpStatusCode

sealed class Resource<T>(val data: T? = null, val httpStatusCode: HttpStatusCode? = null) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(errorCode: HttpStatusCode, data: T? = null) : Resource<T>(data, errorCode)
}

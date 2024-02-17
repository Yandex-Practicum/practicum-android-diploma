package ru.practicum.android.diploma.util

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : Resource<T>(data)
    class ServerError<T> : Resource<T>(message = "Server error")
    class InternetError<T> : Resource<T>(message = "Internet error")
}

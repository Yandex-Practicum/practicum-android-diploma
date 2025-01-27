package ru.practicum.android.diploma.domain

sealed class Resource<T>(
    val value: T? = null,
    val errorCode: Int? = null
) {
    class Success<T>(value: T) : Resource<T>(value)
    class Error<T>(errorCode: Int, value: T? = null) : Resource<T>(value, errorCode)
}

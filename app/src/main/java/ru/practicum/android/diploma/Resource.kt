package ru.practicum.android.diploma

import ru.practicum.android.diploma.domain.models.ErrorNetwork

sealed class Resource<T>(val data: T? = null, val message: ErrorNetwork? = null) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: ErrorNetwork, data: T? = null) : Resource<T>(data, message)
}

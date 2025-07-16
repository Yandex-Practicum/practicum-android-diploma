package ru.practicum.android.diploma.search.domain.model

sealed interface Resource<T> {
    data class Success<T>(val data: T) : Resource<T>
    data class Failed<T>(val message: FailureType) : Resource<T>
}

sealed class FailureType {
    object NotFound : FailureType()
    object NoInternet : FailureType()
    object ApiError : FailureType()
}

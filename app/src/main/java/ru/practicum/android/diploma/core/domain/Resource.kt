package ru.practicum.android.diploma.core.domain

sealed class Resource<out T> {
    data class Success<T>(
        val data: T
    ) : Resource<T>()

    data class Error(
        val code: Int? = null,
    ) : Resource<Nothing>()

    object Loading : Resource<Nothing>()
}

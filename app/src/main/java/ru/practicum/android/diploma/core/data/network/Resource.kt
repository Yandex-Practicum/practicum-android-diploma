package ru.practicum.android.diploma.core.data.network

sealed class Resource<out T> {
    data class Success<T>(
        val data: T
    ) : Resource<T>()

    data class Error(
        val message: String,
        val code: Int? = null,
    ) : Resource<Nothing>()

    object Loading : Resource<Nothing>()
}

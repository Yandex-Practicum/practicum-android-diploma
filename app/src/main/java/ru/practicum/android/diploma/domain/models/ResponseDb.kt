package ru.practicum.android.diploma.domain.models

sealed class ResponseDb<out T> {
    object Loading : ResponseDb<Nothing>() {
        fun <T> cast(): ResponseDb<T> = this as ResponseDb<T>
    }
    data class Success<T>(val data: T) : ResponseDb<T>()
    data class Error(val throwable: Throwable) : ResponseDb<Nothing>()
}

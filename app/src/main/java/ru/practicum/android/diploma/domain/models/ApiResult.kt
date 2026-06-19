package ru.practicum.android.diploma.domain.models

sealed interface ApiResult<out T> {
    data object Loading : ApiResult<Nothing>
    data class Success<T>(val data: T) : ApiResult<T>
    data class Error(val httpCode: Int) : ApiResult<Nothing>
}

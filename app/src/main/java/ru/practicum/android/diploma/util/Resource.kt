package ru.practicum.android.diploma.util

sealed interface Resource<out T> {
    data class Success<T>(val data: T) : Resource<T>
    data class Error(val message: String? = null, val code: Int? = null) : Resource<Nothing>
    data object Loading : Resource<Nothing>
}

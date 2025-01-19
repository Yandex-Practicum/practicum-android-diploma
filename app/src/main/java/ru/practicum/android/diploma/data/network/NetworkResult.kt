package ru.practicum.android.diploma.data.network

sealed class NetworkResult {
    data class Success(val data: Any) : NetworkResult()
    data object Empty : NetworkResult()
    data object Error : NetworkResult()
    data object NetworkException : NetworkResult()
}

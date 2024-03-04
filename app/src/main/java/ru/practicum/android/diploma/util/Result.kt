package ru.practicum.android.diploma.util

sealed interface Result<DATA, ERROR> {
    data class Error<DATA, ERROR>(val errorType: ERROR, val description: String = "") : Result<DATA, ERROR>
    data class Success<DATA, ERROR>(val data: DATA) : Result<DATA, ERROR>
}

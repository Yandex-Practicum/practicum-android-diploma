package ru.practicum.android.diploma.util

import androidx.annotation.StringRes

sealed interface SearchResultData<T> {
    data class Data<T>(val value: T?) : SearchResultData<T>
    data class ServerError<T>(@StringRes val message: Int) : SearchResultData<T>
    data class NoConnection<T>(@StringRes val message: Int) : SearchResultData<T>
}

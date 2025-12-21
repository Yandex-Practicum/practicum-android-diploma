package ru.practicum.android.diploma.search.domain.model

import ru.practicum.android.diploma.search.data.network.Resource
import java.lang.Exception

sealed class Result<out T>() {
    class Success<T>(val data: T) : Result<T>()
    class Error(val message: String, val exception: Exception? = null) : Result<Nothing>()
}

fun <T> Resource<T>.toResult(): Result<T> =
    when (this) {
        is Resource.Success ->

            Result.Success(data = data)

        is Resource.Error ->
            Result.Error(message, exception = exception)
    }

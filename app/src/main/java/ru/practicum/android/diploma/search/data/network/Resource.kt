package ru.practicum.android.diploma.search.data.network

import java.lang.Exception

sealed class Resource<out T> {
    class Success<T>(val data: T) : Resource<T>()
    class Error(val message: String, val exception: Exception? = null) : Resource<Nothing>()
}

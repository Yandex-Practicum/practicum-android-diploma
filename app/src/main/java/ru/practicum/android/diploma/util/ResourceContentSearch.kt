package ru.practicum.android.diploma.util

sealed interface ResourceContentSearch<T> {

    class SuccessSearch<T>(val data: T?) : ResourceContentSearch<T>

    class ErrorSearch<T>(val message: Int?, val data: T? = null) : ResourceContentSearch<T>

    class ServerErrorSearch<T>(val message: Int?, val data: T? = null) : ResourceContentSearch<T>

}

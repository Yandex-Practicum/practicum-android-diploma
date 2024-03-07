package ru.practicum.android.diploma.util

sealed interface Resource<T> {

    class Success<T>(val data: T?) : Resource<T>

    class Error<T>(val message: Int?, val data: T? = null) : Resource<T>

}

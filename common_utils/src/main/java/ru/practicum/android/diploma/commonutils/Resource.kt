package ru.practicum.android.diploma.commonutils

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)

    companion object {
        fun <T> handleResource(result: Resource<T>) : Pair<T?, String?> {
            return when (result) {
                is Success -> Pair(result.data, null)
                is Error -> Pair(null, result.message)
            }
        }
    }
}

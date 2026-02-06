package ru.practicum.android.diploma.data.search

sealed class Resource<T>(
    val data: T? = null,
    val code: Int? = null,
    val totalFound: Int? = null,
    val totalPages: Int? = null
) {
    class Success<T>(
        data: T,
        totalFound: Int,
        totalPages: Int
    ) : Resource<T>(
        data = data,
        code = null,
        totalFound = totalFound,
        totalPages = totalPages
    )

    class Error<T>(
        code: Int,
        data: T? = null
    ) : Resource<T>(
        data = data,
        code = code,
        totalFound = null,
        totalPages = null
    )
}

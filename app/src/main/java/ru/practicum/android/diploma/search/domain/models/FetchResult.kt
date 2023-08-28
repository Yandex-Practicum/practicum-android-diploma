package ru.practicum.android.diploma.search.domain.models

sealed class FetchResult(
    val data: List<Vacancy>? = null,
    val error: NetworkError? = null
) {
    class Success(data: List<Vacancy>): FetchResult(data = data)
    class Error(error: NetworkError): FetchResult(error = error)
}

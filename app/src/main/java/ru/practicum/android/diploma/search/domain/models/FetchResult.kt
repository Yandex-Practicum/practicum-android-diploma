package ru.practicum.android.diploma.search.domain.models

sealed class FetchResult(
    val data: List<Vacancy>? = null,
    val count: Int? = null,
    val error: NetworkError? = null,
) {
    class Success(
        data: List<Vacancy>,
        count: Int? = null,
    ) : FetchResult(data = data, count = count)
    
    class Error(error: NetworkError) : FetchResult(error = error)
}

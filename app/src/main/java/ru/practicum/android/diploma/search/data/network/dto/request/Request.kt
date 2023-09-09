package ru.practicum.android.diploma.search.data.network.dto.request

sealed interface Request {
    object AllCountriesRequest : Request
    class VacanciesRequest(
        val query: String
    ) : Request
}
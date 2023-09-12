package ru.practicum.android.diploma.search.data.network.dto.request

sealed interface Request {
    object AllCountriesRequest : Request
    class VacanciesRequest(
        val queryParams: Map<String, String>,
    ) : Request
    class RegionRequest(val id: String) : Request
    object AllIndustriesRequest: Request
}
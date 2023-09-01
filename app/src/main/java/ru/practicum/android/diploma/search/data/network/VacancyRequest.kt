package ru.practicum.android.diploma.search.data.network


sealed interface VacancyRequest {
    class FullInfoRequest(val id : String): VacancyRequest
    class SearchVacanciesRequest(val query: String): VacancyRequest
}
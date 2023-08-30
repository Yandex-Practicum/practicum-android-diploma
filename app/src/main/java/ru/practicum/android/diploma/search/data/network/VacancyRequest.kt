package ru.practicum.android.diploma.search.data.network


sealed interface VacancyRequest {
    class FullInfoRequest(val id : Long): VacancyRequest
    class SearchVacanciesRequest(val query: String): VacancyRequest
}
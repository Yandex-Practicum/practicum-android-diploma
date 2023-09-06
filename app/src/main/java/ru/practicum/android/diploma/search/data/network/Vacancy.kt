package ru.practicum.android.diploma.search.data.network


sealed interface Vacancy {
    class FullInfoRequest(val id : String): Vacancy
    class SearchRequest(val query: String): Vacancy
    class SimilarVacanciesRequest(val id: String): Vacancy
}
package ru.practicum.android.diploma.search.data.dto

data class SearchResponse(
    val items: List<SearchResponseVacancy>, // лист вакансий
    val found: Int, // количество найденных вакансий
    val pages: Int,
    val perPage: Int
)

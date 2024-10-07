package ru.practicum.android.diploma.search.data.dto

data class SearchResponse(
    val items: List<SearchResponseVacancy>, // Список вакансий
    val found: Int, // Общее количество найденных вакансий
    val page: Int, // Номер текущей страницы
    val pages: Int, // Общее количество страниц
)

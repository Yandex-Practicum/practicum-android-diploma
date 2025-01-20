package ru.practicum.android.diploma.search.domain.model

data class SearchQueryParams (
    val text: String, // строка ввода
    val page: Int = 0, // Страница списка
)

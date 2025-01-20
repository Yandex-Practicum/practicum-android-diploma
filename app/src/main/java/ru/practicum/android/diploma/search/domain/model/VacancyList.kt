package ru.practicum.android.diploma.search.domain.model

data class VacancyList(
    val items: List<VacancyItems>, // список вакансий
    val found: Int, // число найденных вакансий
    val pages: Int, // число найденных страниц
    val page: Int, // текущая отображаемая страница
    val perPage: Int // число вакансий на странице. Должно быть 20
)

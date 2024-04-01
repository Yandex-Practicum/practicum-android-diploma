package ru.practicum.android.diploma.domain.models.vacacy


data class VacancyResponse(
    val items: List<Vacancy>?, // Список вакансий
    val found: Int, // Найдено результатов
    val page: Int, // Номер страницы
    val pages: Int, // Всего страниц
    val perPage: Int
)

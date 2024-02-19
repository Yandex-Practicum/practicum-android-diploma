package ru.practicum.android.diploma.data.dto.responseUnits

data class VacanciesListDto(
    val vacancyDto: Array<VacancyDto>, // Список вакансий
    val found: Int, // Найдено результатов
    val page: Int, // Номер страницы
    val pages: Int, // Всего страниц
    val per_page: Int, // Результатов на странице
)

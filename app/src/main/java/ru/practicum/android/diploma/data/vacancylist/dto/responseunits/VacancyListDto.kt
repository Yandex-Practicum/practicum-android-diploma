package ru.practicum.android.diploma.data.dto.responseUnits


data class VacancyListDto(
    val vacancyDto: List<VacancyDto>?, // Список вакансий
    val found: Int, // Найдено результатов
    val page: Int, // Номер страницы
    val pages: Int, // Всего страниц
)

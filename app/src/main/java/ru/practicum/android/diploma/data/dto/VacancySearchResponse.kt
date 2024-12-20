package ru.practicum.android.diploma.data.dto

import ru.practicum.android.diploma.data.dto.model.VacancyItemDto

class VacancySearchResponse(
    val items: List<VacancyItemDto>, // Список вакансий
    val found: Int, // Количество найденных вакансий
    val pages: Int, // Всего страниц (по 20 вакансий на страницу)
    val page: Int // Номер текущей страницы
) : Response()

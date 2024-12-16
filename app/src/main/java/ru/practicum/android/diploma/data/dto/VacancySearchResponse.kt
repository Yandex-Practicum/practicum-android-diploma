package ru.practicum.android.diploma.data.dto

import ru.practicum.android.diploma.data.dto.model.VacancyDto

class VacancySearchResponse(
    val items: List<VacancyDto>, // Список вакансий
    val found: Long, // Количество найденных вакансий
    val pages: Int, // Всего страниц (по 20 вакансий на страницу)
    val page: Int // Номер текущей страницы
) : Response()

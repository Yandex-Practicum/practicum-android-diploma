package ru.practicum.android.diploma.data.vacancies.dto

import com.google.gson.annotations.SerializedName
import ru.practicum.android.diploma.data.vacancies.dto.list.VacancyDto
import ru.practicum.android.diploma.data.vacancies.response.Response

data class VacanciesSearchResponse(
    val items: List<VacancyDto>?, // Список вакансий
    val found: Int, // Найдено результатов
    val page: Int, // Номер страницы
    val pages: Int, // Всего страниц
    @SerializedName("per_page")
    val perPage: Int
) : Response()

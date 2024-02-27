package ru.practicum.android.diploma.data.vacancylist.dto

import com.google.gson.annotations.SerializedName
import ru.practicum.android.diploma.data.Response
import ru.practicum.android.diploma.data.dto.responseUnits.VacancyDto

data class VacanciesSearchResponse(
    val items: List<VacancyDto>?, // Список вакансий
    val found: Int, // Найдено результатов
    val page: Int, // Номер страницы
    val pages: Int, // Всего страниц
    @SerializedName("per_page")
    val perPage: Int
) : Response()

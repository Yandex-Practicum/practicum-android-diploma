package ru.practicum.android.diploma.common.data.dto

import com.google.gson.annotations.SerializedName
import ru.practicum.android.diploma.search.data.dto.model.SearchVacancyDto

data class SearchVacancyResponse(
    @SerializedName("items") val items: ArrayList<SearchVacancyDto>, // список вакансий
    @SerializedName("found") val found: Int? = null, // число найденных вакансий
    @SerializedName("pages") val pages: Int? = null, // число найденных страниц
    @SerializedName("page") val page: Int? = null, // текущая отображаемая страница
    @SerializedName("per_page") val perPage: Int? = null, // число вакансий на странице.
) : Response()

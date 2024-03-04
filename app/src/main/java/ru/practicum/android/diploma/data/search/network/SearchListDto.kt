package ru.practicum.android.diploma.data.search.network

import com.google.gson.annotations.SerializedName
import ru.practicum.android.diploma.data.dto.fields.VacancyDto

data class SearchListDto(
    @SerializedName("items") val results: ArrayList<VacancyDto>,
    @SerializedName("page") val page: Int?,
    @SerializedName("pages") val pages: Int?,
    @SerializedName("found") val found: Int?
) : Response()

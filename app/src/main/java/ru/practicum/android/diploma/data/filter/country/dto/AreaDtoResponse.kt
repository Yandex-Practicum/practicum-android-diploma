package ru.practicum.android.diploma.data.filter.country.dto

import com.google.gson.annotations.SerializedName
import ru.practicum.android.diploma.data.vacancies.response.Response

data class AreaDtoResponse(
    val id: String,
    @SerializedName("parent_id")
    val parentId: String?,
    val name: String?,
    val areas: List<AreaDtoResponse>
) : Response()

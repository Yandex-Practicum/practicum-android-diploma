package ru.practicum.android.diploma.data.country.dto

import com.google.gson.annotations.SerializedName
import ru.practicum.android.diploma.data.vacancies.response.Response

data class AreaDto(
    val id: String,
    @SerializedName("parent_id")
    val parentId: String?,
    val name: String?,
    val areas: List<AreaDto>
) : Response()

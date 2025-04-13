package ru.practicum.android.diploma.data.network.dto

import com.google.gson.annotations.SerializedName
import ru.practicum.android.diploma.data.dto.ApiResponse
import ru.practicum.android.diploma.data.dto.Vacancy

data class SearchVacanciesResponse(
    var found: Int? = null,
    var items: ArrayList<Vacancy> = arrayListOf(),
    var page: Int? = null,
    var pages: Int? = null,
    @SerializedName("per_page")
    var perPage: Int? = null
) : ApiResponse()

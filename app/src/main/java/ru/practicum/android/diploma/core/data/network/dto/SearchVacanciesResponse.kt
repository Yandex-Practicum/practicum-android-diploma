package ru.practicum.android.diploma.core.data.network.dto

import com.google.gson.annotations.SerializedName

class SearchVacanciesResponse(
    @SerializedName("items")
    val vacancies: List<ShortVacancyDto>,
    @SerializedName("found")
    val numOfResults: Int
) : Response()

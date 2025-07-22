package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName

class VacanciesResponse(
    @SerializedName("items") val vacanciesList: List<VacancyDto>
): Response()

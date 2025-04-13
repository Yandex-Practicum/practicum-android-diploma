package ru.practicum.android.diploma.data.network.dto

import com.google.gson.annotations.SerializedName

data class GetVacancyDetailsRequest(
    @SerializedName("vacancy_id") var vacancyId: String
)

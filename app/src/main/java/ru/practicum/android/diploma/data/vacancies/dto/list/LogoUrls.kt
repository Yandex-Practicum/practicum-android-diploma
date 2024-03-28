package ru.practicum.android.diploma.data.vacancies.dto.list

import com.google.gson.annotations.SerializedName

data class LogoUrls(
    @SerializedName("90")
    val art90: String?,
    @SerializedName("240")
    val art240: String?,
    val original: String
)

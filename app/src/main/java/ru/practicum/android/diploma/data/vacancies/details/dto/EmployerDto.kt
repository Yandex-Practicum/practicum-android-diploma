package ru.practicum.android.diploma.data.vacancies.details.dto

import com.google.gson.annotations.SerializedName
import ru.practicum.android.diploma.data.vacancies.dto.list.LogoUrlsDto

data class EmployerDto(
    val id: String,
    @SerializedName("logo_urls")
    val logoUrls: LogoUrlsDto?,
    val name: String,
    val trusted: Boolean,
    val vacanciesUrl: String?
)

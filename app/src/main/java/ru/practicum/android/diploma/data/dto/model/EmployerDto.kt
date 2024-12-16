package ru.practicum.android.diploma.data.dto.model

import com.google.gson.annotations.SerializedName

data class EmployerDto(
    val name: String, // Название компании
    @SerializedName("logo_urls")
    val logoUrls: LogoUrlDto? // Список ссылок на лого компании разных размеров
)

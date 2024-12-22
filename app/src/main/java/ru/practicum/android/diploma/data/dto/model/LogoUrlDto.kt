package ru.practicum.android.diploma.data.dto.model

import com.google.gson.annotations.SerializedName

data class LogoUrlDto(
    @SerializedName("90")
    val logo90pxUrl: String, // Лого размером меньше 90 px по меньшей стороне
    @SerializedName("240")
    val logo240pxUrl: String, // Ссылка на лого размером менее 240 пикселей по меньшей стороне
    val original: String // Ссылка на необработанный лого
)

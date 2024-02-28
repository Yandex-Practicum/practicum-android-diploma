package ru.practicum.android.diploma.data.dto.responseUnits

import com.google.gson.annotations.SerializedName
import ru.practicum.android.diploma.data.vacancylist.dto.LogoUrlRemote

data class Employer(
    val id: String,
    @SerializedName("logo_urls")
    val logoUrls: LogoUrlRemote?,
    val name: String,
    val trusted: Boolean,
)

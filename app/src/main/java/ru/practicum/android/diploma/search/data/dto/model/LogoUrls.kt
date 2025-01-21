package ru.practicum.android.diploma.search.data.dto.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class LogoUrls(
    @SerializedName("90") val iconSmall: String? = null,
    @SerializedName("240") val iconBig: String? = null,
    val original: String? = null,
) : Serializable

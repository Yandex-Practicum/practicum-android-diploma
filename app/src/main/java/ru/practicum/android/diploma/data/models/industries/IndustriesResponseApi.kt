package ru.practicum.android.diploma.data.models.industries

import com.google.gson.annotations.SerializedName

data class IndustriesResponseApi(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String
)

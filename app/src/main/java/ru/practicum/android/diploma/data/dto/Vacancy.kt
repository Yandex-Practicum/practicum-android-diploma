package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName

data class Vacancy(
    val id: String,
    val name: String,
    @SerializedName("employer_name") val employerName: String,
    @SerializedName("salary_from") val salaryFrom: Int?,
    @SerializedName("salary_to") val salaryTo: Int?,
    val currency: String?,
    val area: Area,
    @SerializedName("published_at") val publishedAt: String,
    val url: String
)



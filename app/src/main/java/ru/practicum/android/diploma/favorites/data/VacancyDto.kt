package ru.practicum.android.diploma.favorites.data

import com.google.gson.annotations.SerializedName

data class VacancyDto(
    val id: String,
    val name: String,
    val description: String?,

    @SerializedName("area_name")
    val areaName: String?,

    @SerializedName("employer_name")
    val employerName: String?,

    @SerializedName("employer_logo")
    val employerLogo: String?,

    @SerializedName("salary_from")
    val salaryFrom: Int?,

    @SerializedName("salary_to")
    val salaryTo: Int?,

    val currency: String?,

    val experience: String?,
    val employment: String?,
    val schedule: String?,

    @SerializedName("key_skills")
    val keySkills: List<String>?,

    val url: String?
)

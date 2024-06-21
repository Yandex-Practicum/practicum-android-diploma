package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName

data class VacancyDetails(
    val id: String,
    val name: String,
    val employer: Employer,
    val salary: Salary?,
    val area: Area,
    val publishedAt: String,
    val url: String?,
    val description: String?,
    @SerializedName("key_skills")
    val keySkills: List<KeySkill>?,
    val contacts: Contacts?,
    val experience: Experience?,
    val employment: Employment
)

data class KeySkill(
    val name: String
)

data class Contacts(
    val name: String?,
    val email: String?,
    val phones: List<Phone>?
)

data class Phone(
    val formatted: String
)

data class Experience(
    val id: String,
    val name: String
)

data class Employment(
    val name: String
)

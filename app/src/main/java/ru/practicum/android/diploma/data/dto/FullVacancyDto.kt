package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName

data class FullVacancyDto(
    val id: String,
    val name: String,
    val area: Area,
    @SerializedName("branded_description")
    val brandedDescription: String?,
    val contacts: Contacts,
    val description: String?,
    val employer: Employer,
    val experience: Experience?,
    val salary: Salary?,
    @SerializedName("key_skills")
    val keySkills: List<SkillName>
)

data class SkillName(
    val name: String?
)

data class Contacts(
    val email: String?,
    val name: String?,
    val phones: List<Phone>,
)

data class Phone(
    val city: String?,
    val comment: String?,
    val country: String?,
    val number: String?
)

data class Experience(
    val id: String?,
    val name: String?,
)
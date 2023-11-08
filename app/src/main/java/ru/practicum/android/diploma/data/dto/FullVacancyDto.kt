package ru.practicum.android.diploma.data.dto

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import ru.practicum.android.diploma.domain.models.mok.EmployerModel
import ru.practicum.android.diploma.domain.models.mok.Salary


data class FullVacancyDto(
    val id: String,
    val name: String,
    val address: Adress,
    @SerializedName("branded_description")
    val brandedDescription: String?,
    val contacts: Contacts,
    val description: String?,
    val employer: EmployerModel?,
    val experience: Experience?,
    val salary: Salary?,
    @SerializedName("key_skills")
    val keySkills: List<SkillName>,
    @SerializedName("driver_license_types")
    val driverLicense: List<License>,
    val languages: List<Language>,
)

fun parseFullVacancy(jsonStr: String): FullVacancyDto? {
    val gson = Gson()
    return gson.fromJson(jsonStr, FullVacancyDto::class.java)
}

data class SkillName(
    val name: String?
)

data class License(
    val id: String?
)

data class Language (
    val name: String?,
    val level: Level,
)

data class Level(
    val id: String?,
    val name: String?
    )

data class Adress(
    val city: String,
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

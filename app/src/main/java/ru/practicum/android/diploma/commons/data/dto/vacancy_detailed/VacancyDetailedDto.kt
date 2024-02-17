package ru.practicum.android.diploma.commons.data.dto.vacancy_detailed

import com.google.gson.annotations.SerializedName
import ru.practicum.android.diploma.commons.data.dto.vacancies_search.Address
import ru.practicum.android.diploma.commons.data.dto.vacancies_search.Company
import ru.practicum.android.diploma.commons.data.dto.vacancies_search.Salary

data class VacancyDetailedDto(
    val id: String,
    val name: String,
    val address: Address?,
    val salary: Salary?,
    @SerializedName("employer") val company: Company,
    val experience: Experience?,
    val employment: Employment?,
    val description: String,
    @SerializedName("key_skills") val keySkills: ArrayList<Skill>,
    val contacts: Contacts?
)

data class Experience(
    val name: String
)

data class Employment(
    val name: String
)

data class Skill(
    val name: String
)

data class Contacts(
    val email: String?,
    val name: String?,
    val phones: ArrayList<Phone>?
)

data class Phone(
    val country: String,
    val city: String,
    val number: String,
    val comment: String?
)

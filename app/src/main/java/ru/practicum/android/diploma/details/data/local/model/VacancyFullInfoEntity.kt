package ru.practicum.android.diploma.details.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_vacancies_full_info")
data class VacancyFullInfoEntity(
    @PrimaryKey
    val id: String = "",
    val experience: String = "",
    val employment: String = "",
    val schedule: String = "",
    val description: String = "",
    val keySkills: String = "",
    val area: String = "",
    val salary: String = "",
    val date: String = "",
    val company: String = "",
    val logo: String = "",
    val title: String = "",
    val contactEmail: String = "",
    val contactName: String = "",
    val contactComment: String = "",
    val contactPhones: String = "",
    val alternateUrl: String? = ""
)

package ru.practicum.android.diploma.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_vacancies_table")
data class FavoriteVacancy(
    @PrimaryKey val code: String,
    val areas: Array<String?>?,
    val addressCity: String?,
    val currencyName: String?,
    val description: String?,
    val employer: String?,
    val employment: String?,
    val experience: String?,
    val keySkills: Array<String?>?,
    val industry: String?,
    val name: String?,
    val salaryCurrency: String?,
    val salaryFrom: Int?,
    val salaryTo: Int?,
    val salaryGross: Boolean?
)

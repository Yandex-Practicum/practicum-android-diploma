package ru.practicum.android.diploma.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

const val FAV_VACANCIES_TABLE_NAME = "fav_vacancies"

@Entity(tableName = FAV_VACANCIES_TABLE_NAME)
data class FavVacancyEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val areaId: String,
    val areaName: String,
    val description: String,
    val employerId: String,
    val employerName: String,
    val employerLogoUrl: String,
    val keySkills: String,
    val salaryFrom: Int,
    val salaryGross: Boolean,
    val salaryTo: String,
    val salaryCurrency: String,
    val experienceId: String,
    val experienceName: String
)

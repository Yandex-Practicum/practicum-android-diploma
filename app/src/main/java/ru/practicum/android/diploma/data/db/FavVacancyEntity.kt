package ru.practicum.android.diploma.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

const val FAV_VACANCIES_TABLE_NAME = "fav_vacancies"

@Entity(tableName = FAV_VACANCIES_TABLE_NAME)
data class FavVacancyEntity(
    @PrimaryKey
    var id: String,
    var name: String,
    var areaId: String,
    var areaName: String,
    var description: String,
    var employerId: String,
    var employerName: String,
    var employerLogoUrl: String,
    var salaryFrom: Int,
    var salaryGross: Boolean,
    var salaryTo: String,
    var salaryCurrency: String,
    var experienceId: String,
    var experienceName: String
)

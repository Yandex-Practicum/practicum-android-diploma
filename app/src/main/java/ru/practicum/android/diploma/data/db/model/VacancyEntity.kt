package ru.practicum.android.diploma.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_vacancies_table")
data class VacancyEntity(

    // Vacancy & VacancyDetails entities fields -------------
    @PrimaryKey val id: String,
    val name: String,

    val employerId: String?,
    val employerLogoUrl90: String?,
    val employerLogoUrl240: String?,
    val employerLogoUrlOriginal: String?,
    val employerName: String?,
    val employerIsTrusted: Boolean?,
    val employerVacanciesUrl: String?,

    val salaryCurrency: String?,
    val salaryFrom: Int?,
    val salaryGross: Boolean?,
    val salaryTo: Int?,
    // Vacancy & VacancyDetails entities fields -------------

    // Vacancy entity fields --------------------------------
    /*val departmentId: String?,
    val departmentName: String?,

    val areaId: String,
    val areaName: String,
    val areaUrl: String,

    val typeId: String,
    val typeName: String,*/
    // Vacancy entity fields --------------------------------

    // VacancyDetails entity fields -------------------------
    val city: String?,
    val experience: String?,
    val employment: String?,
    val description: String,

    val contactEmail: String?,
    val contactName: String?,
    val contactPhonesJson: String?,

    val link: String
    // VacancyDetails entity fields -------------------------
)

/*data class VacancyEntity(
    @PrimaryKey
    val id: String,
    val url: String,
    val name: String,
    val area: String,
    val salaryCurrency: String?,
    val salaryFrom: Int?,
    val salaryTo: Int?,
    val salaryGross: Boolean?,
    val experience: String?,
    val schedule: String?,
    val contactName: String?,
    val contactEmail: String?,
    val phones: String?,
    val contactComment: String?,
    val logoUrl: String?,
    val logoUrl90: String?,
    val logoUrl240: String?,
    val address: String?,
    val employerUrl: String?,
    val employerName: String?,
    val employment: String?,
    val keySkills: String?,
    val description: String,
    val salary: String
)*/

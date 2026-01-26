package ru.practicum.android.diploma.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_vacancies")
data class VacancyEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "experience")
    val experience: String?,

    @ColumnInfo(name = "schedule")
    val schedule: String?,

    @ColumnInfo(name = "employment")
    val employment: String?,

    @ColumnInfo(name = "area_name")
    val areaName: String,

    @ColumnInfo(name = "industry_name")
    val industryName: String?,

    @ColumnInfo(name = "skills")
    val skills: String?,

    @ColumnInfo(name = "url")
    val url: String,

    // поля зарплаты
    @ColumnInfo(name = "salary_from")
    val salaryFrom: Int?,

    @ColumnInfo(name = "salary_to")
    val salaryTo: Int?,

    @ColumnInfo(name = "currency")
    val currency: String?,

    @ColumnInfo(name = "salary_title")
    val salaryTitle: String?,

    // поля адреса
    @ColumnInfo(name = "city")
    val city: String?,

    @ColumnInfo(name = "street")
    val street: String?,

    @ColumnInfo(name = "building")
    val building: String?,

    @ColumnInfo(name = "full_address")
    val fullAddress: String?,

    // контакты
    @ColumnInfo(name = "contact_name")
    val contactName: String?,

    @ColumnInfo(name = "email")
    val email: String?,

    @ColumnInfo(name = "phones")
    val phones: String?,

    // работодатель
    @ColumnInfo(name = "employer_name")
    val employerName: String,

    @ColumnInfo(name = "logo_url")
    val logoUrl: String,

)

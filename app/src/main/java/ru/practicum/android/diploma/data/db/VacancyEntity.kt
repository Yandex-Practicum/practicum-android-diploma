package ru.practicum.android.diploma.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "fav_vacancies_table")
@TypeConverters(PhoneListConverter::class, StringListConverter::class)
data class VacancyEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val description: String,
    val salaryFrom: Int?,
    val salaryTo: Int?,
    val salaryCurrency: String?,
    val addressId: String,
    val addressCity: String,
    val addressStreet: String,
    val addressBuilding: String,
    val addressRaw: String,
    val experienceId: String,
    val experienceName: String,
    val scheduleId: String,
    val scheduleName: String,
    val employmentId: String,
    val employmentName: String,
    val contactsId: String,
    val contactsName: String,
    val contactsEmail: String,
    @TypeConverters(PhoneListConverter::class)
    @ColumnInfo(name = "contactsPhones")
    val contactsPhones: List<Phone>,
    val employerId: String,
    val employerName: String,
    val employerLogo: String,
    val areaId: String,
    val areaName: String,
    @TypeConverters(StringListConverter::class)
    @ColumnInfo(name = "skills")
    val skills: List<String>,
    val url: String,
    val industryId: String,
    val industryName: String
)

data class Phone(
    val comment: String?,
    val formatted: String
)

package ru.practicum.android.diploma.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites_table")
data class VacancyEntity(
    @PrimaryKey @ColumnInfo(name = "vacancy_id")
    val vacancyId: Long,
    @ColumnInfo(name = "name")
    val name: String?,
    @ColumnInfo(name = "area")
    val area: String?,
    @ColumnInfo(name = "employer")
    val employer: String?,
    @ColumnInfo(name = "salary")
    val salary: String?,
    @ColumnInfo(name = "experience")
    val experience: String?,
    @ColumnInfo(name = "employmentForm")
    val employmentForm: String?,
    @ColumnInfo(name = "employment")
    val employment: String?,
    @ColumnInfo(name = "schedule")
    val schedule: String?,
    @ColumnInfo(name = "description")
    val description: String?,
    @ColumnInfo(name = "keySkills")
    val keySkills: String?,
    @ColumnInfo(name = "alternateUrl")
    val alternateUrl: String?,
    @ColumnInfo(name = "address")
    val address: String,
    @ColumnInfo(name = "timeStamp")
    val timeStamp: Long = System.currentTimeMillis()
)

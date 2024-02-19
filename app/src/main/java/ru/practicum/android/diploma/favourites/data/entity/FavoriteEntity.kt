package ru.practicum.android.diploma.favourites.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "vacancy_favorites_table")
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Long,
    val name: String,
    val salaryFrom: String,
    val salaryTo: String,
    val currency: String,
    val experience: String,
    val employment: String,
    val workSchedule: String,
    val description: String,
    val keySkills: String,
    val contactName: String,
    val email: String,
    val phone: String,
    val contactComment: String,
    val employerLogoUrl: String,
    val employerName: String,
    val city: String,
    val insertionTime: Long = Date().time
)

package ru.practicum.android.diploma.favourites.data.entity

import androidx.room.Entity

@Entity(tableName = "vacancy_favorites_table")
data class FavoriteEntity(
    val id: Long,
    val name: String,
    val salaryFrom: String,
    val salaryTo: String,
    val currency: String,
    val experience: String,
    val employment: String,
    val workSchedule: String,
    val description: String,
    val keySkills: List<String>,
    val contactName: String,
    val email: String,
    val phone: String,
    val contactComment: String,
    val employerLogoUrl: String,
    val employerName: String,
    val city: String,
    val insertionTime: Long
)



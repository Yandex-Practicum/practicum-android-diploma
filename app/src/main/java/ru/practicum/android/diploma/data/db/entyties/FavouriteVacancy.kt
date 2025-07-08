package ru.practicum.android.diploma.data.db.entyties

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourites_vacancies")
data class FavouriteVacancy(
    @PrimaryKey
    val id: String,
    val name: String,
    val timestamp: Long
)

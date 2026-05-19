package ru.practicum.android.diploma.favorites.data.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_vacancy")
data class FavoriteVacancyEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)

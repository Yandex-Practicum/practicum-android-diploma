package ru.practicum.android.diploma.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourites_vacancy_table")
data class FavouritesVacancyEntity(
    @PrimaryKey @ColumnInfo(name = "vacancy_id") val id: String,
    val uriPickture: String,
    val name: String,
    val employer : String,
    val salary : Int,
)

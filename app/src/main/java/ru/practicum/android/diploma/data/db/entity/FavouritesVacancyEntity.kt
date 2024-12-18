package ru.practicum.android.diploma.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "favourites_vacancy_table")
data class FavouritesVacancyEntity(
    @PrimaryKey @ColumnInfo(name = "vacancy_id") val id: String,
    val uriPicture: String?,
    val name: String,
    val employer: String,
    val salary: String?,
    var isFavorite: Boolean
): Serializable

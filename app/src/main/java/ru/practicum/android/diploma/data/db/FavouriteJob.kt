package ru.practicum.android.diploma.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourites")
data class FavouriteJob(
    @PrimaryKey val id: String,
    val name: String?,
    val employerName: String?,
    val salaryFrom: Int? = null,
    val salaryTo: Int? = null,
    val salaryCurrency: String? = "",
    val area: String?,
    val employment: String?,
    val schedule: String?,
    val experience: String?,
    val keySkills: String?,
    val description: String?,
    @ColumnInfo(name = "insertion_timestamp")
    val insertionTimestamp: Long = System.currentTimeMillis(),
    val url: String?,
)

package ru.practicum.android.diploma.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vacancy_table")
data class VacancyEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "vacancy_id")
    val id:Long = 0L,
    val photo:String = "",
    @ColumnInfo(name = "vacancy_title")
    val title:String,
    val description:String
)

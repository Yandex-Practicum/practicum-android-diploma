package ru.practicum.android.diploma.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vacancies")
data class VacancyEntity(
    @PrimaryKey(autoGenerate = true) val localId: Long = 0L,
    val remoteId: String?,
    val name: String?,
)

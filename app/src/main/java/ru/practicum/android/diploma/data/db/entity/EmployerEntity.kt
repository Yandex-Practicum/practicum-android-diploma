package ru.practicum.android.diploma.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "employer")
data class EmployerEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val logoUrl: String
)

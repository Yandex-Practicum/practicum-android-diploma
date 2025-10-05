package ru.practicum.android.diploma.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class EmployerEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val logoUrl: String?
)

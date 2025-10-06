package ru.practicum.android.diploma.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity
data class EmployerEntity(
    @PrimaryKey
    val pk: UUID = UUID.randomUUID(),
    val id: String,
    val name: String,
    val logoUrl: String?
)

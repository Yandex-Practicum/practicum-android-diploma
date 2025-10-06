package ru.practicum.android.diploma.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity
data class SalaryEntity(
    @PrimaryKey
    val pk: UUID = UUID.randomUUID(),
    val id: Long = 0L,
    val from: Int?,
    val to: Int?,
    val currency: String?
)

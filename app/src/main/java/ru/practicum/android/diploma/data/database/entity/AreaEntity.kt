package ru.practicum.android.diploma.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "area_table")
data class AreaEntity(
    @PrimaryKey @ColumnInfo(name = "primary_key")
    val pk: UUID = UUID.randomUUID(),
    val id: String,
    val name: String
)

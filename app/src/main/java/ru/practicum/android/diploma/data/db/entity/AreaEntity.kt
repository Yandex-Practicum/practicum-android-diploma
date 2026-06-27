package ru.practicum.android.diploma.data.db.entity

import androidx.room.ColumnInfo

data class AreaEntity(
    @ColumnInfo(name = "id")
    val areaId: String?, // ID региона (города)
    @ColumnInfo(name = "name")
    val areaName: String? // Название региона (города)
)

package ru.practicum.android.diploma.data.db.entity

import androidx.room.ColumnInfo


data class EmployerEntity(
    @ColumnInfo(name = "id")
    val companyId: String?, // ID работодателя
    @ColumnInfo(name = "name")
    val companyName: String?, // Название компании
    @ColumnInfo(name = "logo_url")
    val logoUrl: String? // Ссылка на логотип компании
)

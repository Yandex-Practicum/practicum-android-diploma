package ru.practicum.android.diploma.data.db.entity

import androidx.room.ColumnInfo

data class SalaryEntity(
    @ColumnInfo(name = "from")
    val salaryFrom: Long?, // Зарплата "от"
    @ColumnInfo(name = "to")
    val salaryTo: Long?, // Зарплата "до"
    val currency: String? // Валюта зарплаты
)

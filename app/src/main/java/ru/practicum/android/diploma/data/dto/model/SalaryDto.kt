package ru.practicum.android.diploma.data.dto.model

data class SalaryDto(
    val from: Int?, // Нижняя граница зарплаты
    val to: Int?, // Верхняя граница зарплаты
    val currency: String?, // Код валюты из справочника hh.ru
    val gross: Boolean? // Признак, что зарплата указана до вычета налогов
)

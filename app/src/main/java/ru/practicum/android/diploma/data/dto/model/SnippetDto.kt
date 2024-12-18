package ru.practicum.android.diploma.data.dto.model

data class SnippetDto(
    val requirement: String?, // Требования по вакансии (если есть)
    val responsibility: String? // Обязанности по вакансии (если указаны)
)

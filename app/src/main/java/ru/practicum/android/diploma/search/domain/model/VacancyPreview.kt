package ru.practicum.android.diploma.search.domain.model

import ru.practicum.android.diploma.search.data.model.Salary

data class VacancyPreview(
    val id: Int,
    val name: String,
    val employerName: String,
    val from: Int?,
    val to: Int?,
    val currency: String?,
    val url: String?
)

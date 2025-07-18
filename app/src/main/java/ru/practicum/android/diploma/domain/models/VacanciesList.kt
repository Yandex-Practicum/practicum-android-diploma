package ru.practicum.android.diploma.domain.models

data class VacanciesList(
    val page: Int,
    val pages: Int,
    val found: Long,
    val vacanciesList: List<VacanciesPreview>
)

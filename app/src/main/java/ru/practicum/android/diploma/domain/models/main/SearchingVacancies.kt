package ru.practicum.android.diploma.domain.models.main

data class SearchingVacancies (
    val vacancies: List<Vacancy>,
    val pages: Int,
    val page: Int,
    val foundedVacancies: Int
)

package ru.practicum.android.diploma.domain.models.main

import ru.practicum.android.diploma.domain.models.Vacancy

data class SearchingVacancies(
    val vacancies: List<Vacancy>,
    val pages: Int,
    val page: Int,
    val foundedVacancies: Int
)

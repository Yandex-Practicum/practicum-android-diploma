package ru.practicum.android.diploma.domain.models

data class VacancyPage(
    val vacancyList: List<Vacancy>,
    val currPage: Int,
    val fromPages: Int,
    val found: Int,
)

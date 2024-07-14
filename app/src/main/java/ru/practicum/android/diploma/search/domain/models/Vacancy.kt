package ru.practicum.android.diploma.search.domain.models

data class Vacancy(
    val id: Int,
    val name: String,
    val company: String,
    val currency: String,
    val salaryFrom: String,
    val salaryTo: String,
    val area: String,
    val icon: String
)

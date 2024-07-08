package ru.practicum.android.diploma.search.domain.models

data class Vacancy(
    val id: Int,
    val name: String,
    val company: String,
    val salary: String,
    val area: String,
    val icon: String
)

package ru.practicum.android.diploma.search.domain

data class Vacancy(
    val id: Long,
    val iconUri: String = "",
    val title: String = "",
    val company: String = "",
    val salary: String = "",
    val date : Long = 0L
)

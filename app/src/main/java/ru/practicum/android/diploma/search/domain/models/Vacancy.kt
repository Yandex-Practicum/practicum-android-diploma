package ru.practicum.android.diploma.search.domain.models

data class Vacancy(
    val id: Int = 0,
    val iconUri: String = "",
    val title: String = "требуется аndroid ждун",
    val company: String = "Google",
    val salary: String = "300 тыщ",
)

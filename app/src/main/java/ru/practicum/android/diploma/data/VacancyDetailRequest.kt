package ru.practicum.android.diploma.data

data class VacancyDetailRequest(
    val token: String,
    val appName: String,
    val id: String
) : Request(token, appName)

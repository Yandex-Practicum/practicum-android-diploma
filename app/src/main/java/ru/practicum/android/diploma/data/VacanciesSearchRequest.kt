package ru.practicum.android.diploma.data

data class VacanciesSearchRequest(
    val searchOptions: Map<String, String>
) : Request

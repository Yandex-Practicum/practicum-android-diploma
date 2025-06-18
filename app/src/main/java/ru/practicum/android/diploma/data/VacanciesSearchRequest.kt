package ru.practicum.android.diploma.data

data class VacanciesSearchRequest(
    val searchOptions: HashMap<String, String>
) : Request

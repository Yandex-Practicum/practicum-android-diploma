package ru.practicum.android.diploma.data

data class VacanciesSearchRequest(
    val token: String,
    val appName: String,
    val searchOptions: HashMap<String, String>
) : Request(token, appName)

package ru.practicum.android.diploma.data.dto

sealed interface Request {
    data class VacancyRequest(val id: String) : Request
    data class VacanciesRequest(val options: Map<String, String>): Request
}

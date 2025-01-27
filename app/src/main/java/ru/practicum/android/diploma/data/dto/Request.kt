package ru.practicum.android.diploma.data.dto

sealed interface Request {
    data object CountriesRequest : Request
    data object ProfessionalRolesRequest : Request
    data class AreasRequest(val id: String) : Request
    data class VacancyRequest(val id: String) : Request
    data class VacanciesRequest(val options: Map<String, String>) : Request
}

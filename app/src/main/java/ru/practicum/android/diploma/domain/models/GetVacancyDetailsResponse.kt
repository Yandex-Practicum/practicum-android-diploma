package ru.practicum.android.diploma.domain.models

sealed interface GetVacancyDetailsResponse {
    data class Success(val result: VacancyDetail) : GetVacancyDetailsResponse

    data object Error : GetVacancyDetailsResponse

    data object NotFound : GetVacancyDetailsResponse

    data object ServerError : GetVacancyDetailsResponse
}

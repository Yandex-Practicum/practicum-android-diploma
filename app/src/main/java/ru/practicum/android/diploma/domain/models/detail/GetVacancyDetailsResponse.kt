package ru.practicum.android.diploma.domain.models.detail

sealed interface GetVacancyDetailsResponse {
    data class Success(val result: VacancyDetail) : GetVacancyDetailsResponse

    data object Error : GetVacancyDetailsResponse
}

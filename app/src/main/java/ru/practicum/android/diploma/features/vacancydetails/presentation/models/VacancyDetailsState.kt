package ru.practicum.android.diploma.features.vacancydetails.presentation.models

sealed class VacancyDetailsState {
    object Loading : VacancyDetailsState()

    object Error : VacancyDetailsState()

    data class Content(
        val vacancy: VacancyDetailsUiModel,
    ) : VacancyDetailsState()

}

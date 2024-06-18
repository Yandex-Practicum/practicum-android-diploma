package ru.practicum.android.diploma.domain.models

sealed class VacancyViewState {
    object VacancyIsFavorite : VacancyViewState()
    object VacancyIsNotFavorite : VacancyViewState()
    data class VacancyDataDetail(val vacancy: Vacancy) : VacancyViewState()
}

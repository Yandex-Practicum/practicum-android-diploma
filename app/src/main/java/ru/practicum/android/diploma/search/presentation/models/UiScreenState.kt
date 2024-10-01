package ru.practicum.android.diploma.search.presentation.models

sealed interface UiScreenState {
    data object Default : UiScreenState
    data object Loading : UiScreenState
    data class Success(val vacancies: List<VacancyUi>, val found: Int) : UiScreenState
    data object Empty : UiScreenState
    data object ServerError : UiScreenState
    data object NoInternetError : UiScreenState
}

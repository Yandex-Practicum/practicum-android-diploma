package ru.practicum.android.diploma.presentation.ui.search

import ru.practicum.android.diploma.domain.models.Vacancy

sealed class SearchScreenState {
    data object StartScreen : SearchScreenState()
    data object Loading : SearchScreenState()
    data object InternetConnectionError : SearchScreenState()
    data object ServerError : SearchScreenState()
    data object EmptyList : SearchScreenState()
    data object ErrInPagging : SearchScreenState()
    data object LoadNextPage : SearchScreenState()
    data class ShowVacancies(val vacancies: List<Vacancy>) : SearchScreenState()
}

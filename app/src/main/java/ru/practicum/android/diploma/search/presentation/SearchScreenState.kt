package ru.practicum.android.diploma.search.presentation

import ru.practicum.android.diploma.search.domain.models.Vacancy

sealed class SearchScreenState {
    object Default : SearchScreenState()
    object Loading : SearchScreenState()
    data class ShowContent(val vacancies: List<Vacancy>, val found: Int) : SearchScreenState()
    data class Error(val error: UiError) : SearchScreenState()
}

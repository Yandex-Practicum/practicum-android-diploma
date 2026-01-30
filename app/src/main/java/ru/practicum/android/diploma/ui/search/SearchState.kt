package ru.practicum.android.diploma.ui.search

import ru.practicum.android.diploma.domain.models.Vacancy

sealed class SearchState {
    object Initial : SearchState()
    object Loading : SearchState()
    data class Content(val vacancies: List<Vacancy>, val totalFound: Int) : SearchState()
    object Empty : SearchState()
}

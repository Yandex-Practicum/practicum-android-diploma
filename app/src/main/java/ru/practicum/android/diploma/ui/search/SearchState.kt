package ru.practicum.android.diploma.ui.search

import ru.practicum.android.diploma.domain.models.Vacancy

sealed interface SearchState {
    object Loading : SearchState
    data class SearchContent(val vacansys: List<Vacancy>) : SearchState
    object Error : SearchState
    object EmptySearch : SearchState
    object EmptyScreen : SearchState

}

package ru.practicum.android.diploma.ui.search

import ru.practicum.android.diploma.domain.models.Vacancy

sealed interface SearchState {
    data object Loading : SearchState
    data class SearchContent(val vacancys: List<Vacancy>) : SearchState
    data object Error : SearchState
    data object EmptySearch : SearchState
    data object EmptyScreen : SearchState

}

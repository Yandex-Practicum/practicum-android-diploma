package ru.practicum.android.diploma.search.domain.model

import ru.practicum.android.diploma.search.presentation.items.ListItem

sealed interface SearchViewState {
    data class Success(val vacancyList: VacancyList) : SearchViewState
    data class Content(
        val listItem: List<ListItem>,
        val vacanciesFoundHint: String?,
    ) : SearchViewState
    data object NotFoundError : SearchViewState
    data object ServerError : SearchViewState
    data object ConnectionError : SearchViewState
    data object Loading : SearchViewState
    data object Base : SearchViewState
}

package ru.practicum.android.diploma.search.presenter.model

import ru.practicum.android.diploma.search.domain.model.VacancyPreview

sealed interface SearchState {

    data class Content(val data: List<VacancyPreview>) : SearchState
    object NotFound : SearchState
    object Loading : SearchState
    object Error : SearchState
    object Empty : SearchState

}

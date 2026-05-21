package ru.practicum.android.diploma.search.ui

import ru.practicum.android.diploma.core.domain.models.Vacancies

sealed class SearchViewError {
    object Internet : SearchViewError()
    object NotFound : SearchViewError()
}
sealed class SearchViewState {
    object Default : SearchViewState()
    object Loading : SearchViewState()
    class Data(val vacancies: Vacancies) : SearchViewState()
    class Error(val error: SearchViewError) : SearchViewState()
}

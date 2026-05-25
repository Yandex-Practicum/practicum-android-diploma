package ru.practicum.android.diploma.search.ui

import kotlinx.coroutines.flow.StateFlow
import ru.practicum.android.diploma.core.domain.models.Vacancies

sealed class SearchViewError {
    object Internet : SearchViewError()
    object NotFound : SearchViewError()
}
sealed class SearchViewState(var showClearButton: Boolean) {
    object Default : SearchViewState(false)
    object Loading : SearchViewState(true)
    class Data(val vacancies: Vacancies) : SearchViewState(true)
    class Error(val error: SearchViewError) : SearchViewState(true)
}

package ru.practicum.android.diploma.search.presenter.filter.model

import ru.practicum.android.diploma.search.domain.model.FailureType
import ru.practicum.android.diploma.search.domain.model.Industry

sealed interface FieldsState {
    data object Loading : FieldsState
    data class Content(val industries: List<Industry>, val selectedIndustry: Industry?) : FieldsState
    data class Error(val error: FailureType) : FieldsState
    data object Empty : FieldsState
}

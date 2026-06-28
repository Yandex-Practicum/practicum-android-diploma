package ru.practicum.android.diploma.presentation.viewmodels

import ru.practicum.android.diploma.domain.models.Industry

sealed interface IndustryState {
    object IsLoading : IndustryState
    data class Content(val industry: List<Industry>) : IndustryState
    data class Error(val error: String) : IndustryState
    data class Empty(val error: String) : IndustryState
}

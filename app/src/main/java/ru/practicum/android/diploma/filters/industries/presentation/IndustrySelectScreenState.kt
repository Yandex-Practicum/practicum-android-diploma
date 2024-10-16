package ru.practicum.android.diploma.filters.industries.presentation

import ru.practicum.android.diploma.filters.industries.domain.models.Industry

sealed class IndustrySelectScreenState {
    data object Loading : IndustrySelectScreenState()
    data class ChooseItem(val items: List<Industry>) : IndustrySelectScreenState()
    data object ServerError : IndustrySelectScreenState()
    data object NetworkError : IndustrySelectScreenState()
    data object Empty : IndustrySelectScreenState()
    data class FilterRequest(val request: String) : IndustrySelectScreenState()
}

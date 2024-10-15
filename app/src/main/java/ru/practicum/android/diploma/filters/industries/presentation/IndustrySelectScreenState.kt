package ru.practicum.android.diploma.filters.industries.presentation

sealed class IndustrySelectScreenState {
    data class ChooseItem<T>(val items: List<T>) : IndustrySelectScreenState()
    data object ServerError : IndustrySelectScreenState()
    data object NetworkError : IndustrySelectScreenState()
    data object Empty : IndustrySelectScreenState()
}

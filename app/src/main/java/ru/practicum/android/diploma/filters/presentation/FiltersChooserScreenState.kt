package ru.practicum.android.diploma.filters.presentation

sealed class FiltersChooserScreenState {
    data class ChooseItem<T>(val items: List<T>) : FiltersChooserScreenState()
    data object ServerError : FiltersChooserScreenState()
    data object NetworkError : FiltersChooserScreenState()
    data object Empty : FiltersChooserScreenState()
}

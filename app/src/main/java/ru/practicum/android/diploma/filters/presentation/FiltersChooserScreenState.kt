package ru.practicum.android.diploma.filters.presentation

sealed class FiltersChooserScreenState {
    data class ChooseItem(val id: String, val name: String) : FiltersChooserScreenState()
    data object Error : FiltersChooserScreenState()
    data object Empty : FiltersChooserScreenState()
}

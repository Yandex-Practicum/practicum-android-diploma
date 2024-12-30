package ru.practicum.android.diploma.ui.search.viewmodel

sealed interface FilterButtonState {

    data object FilterOff : FilterButtonState

    data object FilterOn : FilterButtonState

}

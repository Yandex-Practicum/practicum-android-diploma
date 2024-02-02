package ru.practicum.android.diploma.ui.search

sealed interface ClearTextState {
    object None : ClearTextState
    object ClearText : ClearTextState
}

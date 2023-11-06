package ru.practicum.android.diploma.presentation.search

sealed class IconState {
    object SearchIcon : IconState()
    object CloseIcon : IconState()

}
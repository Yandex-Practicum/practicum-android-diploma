package ru.practicum.android.diploma.filter.place.presentation.viewmodel.state

internal sealed interface NetworkState {
    data object Success : NetworkState
    data object Empty : NetworkState
    data class Error(val message: String) : NetworkState
}

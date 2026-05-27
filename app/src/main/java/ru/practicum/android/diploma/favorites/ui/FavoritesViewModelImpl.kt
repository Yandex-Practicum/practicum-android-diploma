package ru.practicum.android.diploma.favorites.ui

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FavoritesViewModelImpl : FavoritesViewModel() {
    private val _state = MutableStateFlow<FavoritesViewState>(FavoritesViewState.Empty)
    override var state: StateFlow<FavoritesViewState> = _state.asStateFlow()
}

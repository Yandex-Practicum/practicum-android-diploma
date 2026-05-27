package ru.practicum.android.diploma.favorites.ui.mock

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.practicum.android.diploma.favorites.ui.FavoritesViewModel
import ru.practicum.android.diploma.favorites.ui.FavoritesViewState

class FavoritesViewModelMock(
    mockState: FavoritesViewState,
) : FavoritesViewModel() {
    private val _state = MutableStateFlow<FavoritesViewState>(FavoritesViewState.Empty)
    override var state: StateFlow<FavoritesViewState> = _state.asStateFlow()

    init {
        _state.value = mockState
    }
}

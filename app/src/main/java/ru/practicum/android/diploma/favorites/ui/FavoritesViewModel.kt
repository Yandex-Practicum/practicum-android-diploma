package ru.practicum.android.diploma.favorites.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow

abstract class FavoritesViewModel : ViewModel() {
    abstract var state: StateFlow<FavoritesViewState>
}
